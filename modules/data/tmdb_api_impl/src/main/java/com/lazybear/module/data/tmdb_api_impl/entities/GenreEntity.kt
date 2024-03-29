package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.Genre

data class GenreEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)

fun GenreEntity.toDomain(): Genre {
    return Genre(
        id = id,
        name = name,
    )
}