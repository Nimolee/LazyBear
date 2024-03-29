package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.Genre

data class GenresListEntity(
    @SerializedName("genres") val genres: List<GenreEntity>
)

fun GenresListEntity.toDomain(): List<Genre> {
    return genres.map { it.toDomain() }
}