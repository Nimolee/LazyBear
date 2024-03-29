package com.lazybear.module.data.tmdb_api_impl.endpoints

import app.lazybear.module.data.server.ServerResult
import com.lazybear.module.data.tmdb_api_impl.entities.GenresListEntity
import retrofit2.http.GET

interface GenresEndpoint {
    @GET("genre/movie/list")
    suspend fun getMoviesGenres(): ServerResult<GenresListEntity>
}