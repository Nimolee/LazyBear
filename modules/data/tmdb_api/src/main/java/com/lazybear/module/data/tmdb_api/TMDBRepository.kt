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
    /**
     * Flow that contains list of genres
     */
    val genresFlow: Flow<List<Genre>>

    /**
     * Flow that contains list of release years
     */
    val yearsFlow: Flow<List<ReleaseYear>>

    /**
     * Flow with last recommended movie
     */
    val recommendedMovieFlow: Flow<Movie?>

    /**
     * Load list of genres into the [genresFlow].
     * @param force used to enforce network request. Otherwise success result with previous
     * success result will be returned.
     */
    suspend fun loadGenres(force: Boolean = false): Result<List<Genre>, GenresErrors>

    /**
     * List of release years
     */
    suspend fun getYears(): List<ReleaseYear>

    /**
     * Perform movie recommendation request sequence.
     * [recommendedMovieFlow] value will be reset and auto populated with new value when
     * success result will be achieved
     *
     * @param genres list of genres used to filter
     * @param releaseYear selected release year to filter
     */
    suspend fun recommendMovie(
        genres: List<Genre>,
        releaseYear: ReleaseYear? = null
    ): Result<Movie, RecommendError>

    /**
     * Request information about specific movie by id
     */
    suspend fun getMovie(movieId: Int): Result<Movie, MovieError>
}