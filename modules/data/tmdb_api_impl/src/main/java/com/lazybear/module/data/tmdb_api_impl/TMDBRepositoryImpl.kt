package com.lazybear.module.data.tmdb_api_impl

import app.lazybear.module.data.server.Result
import app.lazybear.module.data.server.ServerResult
import app.lazybear.module.data.server.dropNull
import app.lazybear.module.data.server.map
import app.lazybear.module.data.server.mergeErrors
import app.lazybear.module.data.server.onSuccess
import app.lazybear.module.data.server.toResult
import app.lazybear.module.utils.log.logD
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import com.lazybear.module.data.tmdb_api.errors.GenresErrors
import com.lazybear.module.data.tmdb_api.errors.MovieError
import com.lazybear.module.data.tmdb_api.errors.RecommendError
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
        const val ERROR_CODE_NO_MOVIE = -404
    }

    override val genresFlow: MutableStateFlow<List<Genre>> = MutableStateFlow(emptyList())
    override val yearsFlow: MutableStateFlow<List<ReleaseYear>> =
        MutableStateFlow(ReleaseYearEntity.getYears().map { it.toDomain() })
    override val recommendedMovieFlow: MutableStateFlow<Movie?> = MutableStateFlow(null)

    private suspend fun loadMovie(movieId: Int): ServerResult<Movie> {
        return _scope.async {
            if (movieId == recommendedMovieFlow.value?.id) {
                ServerResult.Success(recommendedMovieFlow.value)
            } else {
                _movieEndpoints.loadMovieDetails(movieId).map {
                    it?.toDomain().also { movie -> recommendedMovieFlow.emit(movie) }
                }.dropNull().dropNull().onSuccess {
                    logD(TAG, "loadMovie") { "loadMovieResult = $it" }
                }
            }
        }.await()
    }

    override suspend fun loadGenres(force: Boolean): Result<List<Genre>, GenresErrors> {
        return _scope.async {
            if (!force && genresFlow.value.isNotEmpty()) {
                ServerResult.Success(genresFlow.value)
            } else {
                val result = _genresEndpoints.getMoviesGenres()
                result.dropNull().map {
                    result.body!!.let {
                        val genres = it.toDomain()
                        genresFlow.emit(genres)
                        genres
                    }
                }.dropNull()
            }.toResult(errorMapper = { GenresErrors.UnknownError },
                networkErrorMapper = { GenresErrors.NetworkError },
                unknownErrorMapper = { GenresErrors.UnknownError })
        }.await()
    }

    override suspend fun getYears(): List<ReleaseYear> {
        return yearsFlow.value
    }

    override suspend fun recommendMovie(
        genres: List<Genre>,
        releaseYear: ReleaseYear?,
    ): Result<Movie, RecommendError> {
        recommendedMovieFlow.emit(null)
        logD(TAG, "recommendMovie") { "Start movie recommendation" }
        val releaseDateAfter = releaseYear?.start?.let { ISO_DATE.format(it) }
        val releaseDateBefore = releaseYear?.end?.let { ISO_DATE.format(it) }
        val genresString = genres.joinToString(",") { it.id.toString() }

        return _scope.async {
            val result = _discoverEndpoints.getDiscoveredMovies(
                genres = genresString,
                releaseDateBefore = releaseDateBefore,
                releaseDateAfter = releaseDateAfter,
            )
            logD(TAG, "recommendMovie") { "$result with $genres" }
            result.map { body ->
                val totalResults = body!!.totalResults
                if (totalResults == 0) return@map ServerResult.Error(ERROR_CODE_NO_MOVIE, "")

                val movieIndex = Random.nextInt(totalResults)
                val page = min(
                    movieIndex / ITEMS_PER_PAGE_IN_DISCOVER + 1 // Pages start from 1
                    , MAX_PAGES_COUNT
                )
                _discoverEndpoints.getDiscoveredMovies(
                    genres = genresString,
                    releaseDateBefore = releaseDateBefore,
                    releaseDateAfter = releaseDateAfter,
                    page = page,
                ).map { idBody ->
                    val movieId = idBody!!.results[movieIndex % ITEMS_PER_PAGE_IN_DISCOVER].id
                    logD(TAG, "recommendMovie") { "Selected movie = $movieId" }
                    movieId
                }
            }.mergeErrors().dropNull().map { id ->
                loadMovie(id!!)
            }.mergeErrors().dropNull().toResult(
                errorMapper = {
                    when (it.code) {
                        ERROR_CODE_NO_MOVIE -> RecommendError.NoResults
                        else -> RecommendError.UnknownError
                    }
                },
                networkErrorMapper = { RecommendError.NetworkError },
                unknownErrorMapper = { RecommendError.UnknownError },
            ).let {
                if (it.isSuccess && it.body?.bad == true) {
                    logD(TAG, "recommendMovie") { "Bad movie found. Try again." }
                    recommendMovie(genres, releaseYear)
                } else {
                    it
                }
            }
        }.await()
    }

    override suspend fun getMovie(movieId: Int): Result<Movie, MovieError> {
        return loadMovie(movieId).toResult(
            errorMapper = { MovieError.UnknownError },
            networkErrorMapper = { MovieError.NetworkError },
            unknownErrorMapper = { MovieError.UnknownError },
        )
    }

}