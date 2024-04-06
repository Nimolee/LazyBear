package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.MovieVideo

data class VideoEntity(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("site") val site: String,
    @SerializedName("type") val type: String,
    @SerializedName("key") val key: String,
)

fun List<VideoEntity>.toDomain(): List<MovieVideo> {
    return this.filter { it.site == "YouTube" }.map { it.toYoutube() }
}

fun VideoEntity.toYoutube(): MovieVideo {
    return MovieVideo(
        id = id,
        videoLink = "${BuildConfig.YOUTUBE_VIDEO_URL}$key",
        thumbnailLink = BuildConfig.YOUTUBE_IMAGE_URL.replace("<video_id>", key),
    )
}

