package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.MovieImage

data class ImageEntity(
    @SerializedName("aspect_ratio") val aspectRatio: Float,
    @SerializedName("file_path") val path: String,
    @SerializedName("iso_639_1") val locale: String,
)

fun ImageEntity.toDomain(): MovieImage {
    return MovieImage(
        aspectRatio = aspectRatio,
        link = "${BuildConfig.IMAGE_URL}$path",
    )
}