package com.lazybear.module.data.tmdb_api

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import kotlinx.coroutines.flow.StateFlow

interface TMDBRepository {
    val genresFlow: StateFlow<List<Genre>>
    val yearsFlow: StateFlow<List<ReleaseYear>>

    suspend fun loadGenres(force: Boolean = false): ServerResult<List<Genre>>

    suspend fun recommendMovie(
        genres: List<Genre>,
        releaseYear: ReleaseYear? = null
    ): ServerResult<Movie>

    suspend fun loadMovie(movieId: Int): ServerResult<Movie>
}