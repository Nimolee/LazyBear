package com.lazybear.module.data.tmdb_api_impl.endpoints

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api_impl.entities.MovieDiscoverEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface DiscoverEndpoint {
    @GET("discover/movie")
    suspend fun getDiscoveredMovies(
        @Query("with_genres") genres: String,
        @Query("release_date.gte") releaseDateAfter: String?,
        @Query("release_date.lte") releaseDateBefore: String?,
        @Query("page") page: Int = 1,
        @Query("include_adult") adult: Boolean = true,
        @Query("watch_region") region: String = "UA",
        @Query("with_watch_providers") providers: String = "2|3|8|192|283|384" //2 - Apple TV, 3 - Google Play Movies, 8 -Netflix, 192 - YouTube, 283 - Crunchyroll, 384 -HBO Max,
    ): ServerResult<MovieDiscoverEntity>
}