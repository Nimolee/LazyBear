package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class ImagesEntity(
    @SerializedName("backdrops") val backdrops: List<ImageEntity>,
    @SerializedName("logos") val logos: List<ImageEntity>,
    @SerializedName("posters") val posters: List<ImageEntity>,
)