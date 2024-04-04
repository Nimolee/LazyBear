package com.lazybear.module.data.tmdb_api_impl.endpoints

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api_impl.entities.CreditsEntity
import com.lazybear.module.data.tmdb_api_impl.entities.MovieEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesEndpoint {
    @GET("movie/{movie_id}")
    suspend fun loadMovieDetails(@Path("movie_id") movieId: Int): ServerResult<MovieEntity>

    @GET("movie/{movie_id}/credits")
    suspend fun loadMovieCredits(@Path("movie_id") movieId: Int): ServerResult<CreditsEntity>
}