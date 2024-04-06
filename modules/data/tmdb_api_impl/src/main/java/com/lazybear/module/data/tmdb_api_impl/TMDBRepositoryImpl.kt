package com.lazybear.module.data.tmdb_api_impl

import app.lazybear.module.data.server.ServerResult
import app.lazybear.module.data.server.dropNull
import app.lazybear.module.data.server.map
import app.lazybear.module.data.server.mergeErrors
import app.lazybear.module.data.server.onSuccess
import app.lazybear.module.utils.log.logD
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import com.lazybear.module.data.tmdb_api_impl.endpoints.DiscoverEndpoint
import com.lazybear.module.data.tmdb_api_impl.endpoints.GenresEndpoint
import com.lazybear.module.data.tmdb_api_impl.endpoints.MoviesEndpoint
import com.lazybear.module.data.tmdb_api_impl.entities.ReleaseYearEntity
import com.lazybear.module.data.tmdb_api_impl.entities.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.format.DateTimeFormatter.ISO_DATE
import kotlin.math.min
import kotlin.random.Random

class TMDBRepositoryImpl(
    private val _genresEndpoints: GenresEndpoint,
    private val _discoverEndpoints: DiscoverEndpoint,
    private val _movieEndpoints: MoviesEndpoint,
    private val _scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : TMDBRepository {
    companion object {
        const val TAG = "TMDBRepositoryImpl"
        const val ITEMS_PER_PAGE_IN_DISCOVER = 20
        const val MAX_PAGES_COUNT = 500
    }

    override val genresFlow: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())
    override val yearsFlow: MutableStateFlow<List<ReleaseYear>> =
        MutableStateFlow(ReleaseYearEntity.getYears().map { it.toDomain() })

    override suspend fun loadGenres(force: Boolean): ServerResult<List<Genre>> {
        return _scope.async {
            if (!force && genresFlow.value.isNotEmpty()) {
                ServerResult.Success(genresFlow.value)
            } else {
                val result = _genresEndpoints.getMoviesGenres()
                if (result.isSuccess) {
                    result as ServerResult.Success
                    result.body?.let {
                        val genres = it.toDomain()
                        genresFlow.emit(genres)
                        ServerResult.Success(genres)
                    } ?: ServerResult.UnknownError
                } else {
                    result as ServerResult.Error
                    result
                }
            }
        }.await()
    }

    override suspend fun recommendMovie(
        genres: List<Genre>,
        releaseYear: ReleaseYear?,
    ): ServerResult<Movie> {
        logD(TAG, "recommendMovie") { "Start movie recommendation" }
        val releaseDateAfter = releaseYear?.start?.let { ISO_DATE.format(it) }
        val releaseDateBefore = releaseYear?.end?.let { ISO_DATE.format(it) }
        return _scope.async {
            val genresString = genres.joinToString(",") { it.id.toString() }
            val result = _discoverEndpoints.getDiscoveredMovies(
                genres = genresString,
                releaseDateBefore = releaseDateBefore,
                releaseDateAfter = releaseDateAfter,
            )
            logD(TAG, "recommendMovie") { "$result with $genres" }
            result.map { body ->
                val totalResults = body!!.totalResults
                val movie = Random.nextInt(totalResults)
                val page = min(
                    movie / ITEMS_PER_PAGE_IN_DISCOVER + 1,// Pages start from 1
                    MAX_PAGES_COUNT
                )
                val idResult = _discoverEndpoints.getDiscoveredMovies(
                    genres = genresString,
                    releaseDateBefore = releaseDateBefore,
                    releaseDateAfter = releaseDateAfter,
                    page = page,
                )
                idResult.map { idBody ->
                    val movieId = idBody!!.results[movie % ITEMS_PER_PAGE_IN_DISCOVER].id
                    logD(TAG, "recommendMovie") { "Selected movie = $movieId" }
                    movieId
                }
            }.mergeErrors().dropNull().map { id ->
                loadMovie(id!!)
            }.mergeErrors().dropNull()
        }.await()
    }

    override suspend fun loadMovie(movieId: Int): ServerResult<Movie> {
        return _scope.async {
            _movieEndpoints.loadMovieDetails(movieId).map {
                it?.toDomain()
            }.dropNull().dropNull().onSuccess {
                logD(TAG, "loadMovie") { "loadMovieResult = $it" }
            }
        }.await()
    }
}