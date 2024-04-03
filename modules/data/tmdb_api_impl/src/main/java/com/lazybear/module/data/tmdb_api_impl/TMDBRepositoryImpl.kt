package com.lazybear.module.data.tmdb_api_impl

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import com.lazybear.module.data.tmdb_api_impl.endpoints.GenresEndpoint
import com.lazybear.module.data.tmdb_api_impl.entities.ReleaseYearEntity
import com.lazybear.module.data.tmdb_api_impl.entities.toDomain
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow

class TMDBRepositoryImpl(
    private val _genresEndpoints: GenresEndpoint,
    private val _scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
) : TMDBRepository {
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
                    } ?: ServerResult.UnknownError()
                } else {
                    result as ServerResult.Error
                    result
                }
            }
        }.await()
    }
}