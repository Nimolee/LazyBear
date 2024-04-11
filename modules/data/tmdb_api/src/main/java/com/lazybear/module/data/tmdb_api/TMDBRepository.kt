package com.lazybear.module.data.tmdb_api

import app.lazybear.module.data.server.Result
import com.lazybear.module.data.tmdb_api.entities.Genre
import com.lazybear.module.data.tmdb_api.entities.Movie
import com.lazybear.module.data.tmdb_api.entities.ReleaseYear
import com.lazybear.module.data.tmdb_api.errors.GenresErrors
import com.lazybear.module.data.tmdb_api.errors.MovieError
import com.lazybear.module.data.tmdb_api.errors.RecommendError
import kotlinx.coroutines.flow.Flow

interface TMDBRepository {
    val genresFlow: Flow<List<Genre>>
    val yearsFlow: Flow<List<ReleaseYear>>
    val recommendedMovieFlow: Flow<Movie?>

    suspend fun loadGenres(force: Boolean = false): Result<List<Genre>, GenresErrors>

    suspend fun getYears(): List<ReleaseYear>

    suspend fun recommendMovie(
        genres: List<Genre>,
        releaseYear: ReleaseYear? = null
    ): Result<Movie, RecommendError>

    suspend fun getMovie(movieId: Int): Result<Movie, MovieError>
}