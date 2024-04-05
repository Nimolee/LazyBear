package com.lazybear.module.data.tmdb_api_impl.endpoints

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api_impl.entities.MovieEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesEndpoint {
    @GET("movie/{movie_id}")
    suspend fun loadMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") append: String = "videos,images,credits,watch/providers"
    ): ServerResult<MovieEntity>
}