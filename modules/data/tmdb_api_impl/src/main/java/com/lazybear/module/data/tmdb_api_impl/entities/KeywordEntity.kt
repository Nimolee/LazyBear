package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.Keyword

data class KeywordEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
)

fun KeywordEntity.toDomain(): Keyword {
    return Keyword(
        id = id,
        name = name,
    )
}