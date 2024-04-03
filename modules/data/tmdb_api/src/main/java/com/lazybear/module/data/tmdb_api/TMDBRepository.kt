package com.lazybear.module.data.tmdb_api

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    val genresFlow: Flow<List<Genre>>
    val yearsFlow: Flow<List<ReleaseYear>>

    suspend fun loadGenres(force: Boolean = false): ServerResult<List<Genre>>
}