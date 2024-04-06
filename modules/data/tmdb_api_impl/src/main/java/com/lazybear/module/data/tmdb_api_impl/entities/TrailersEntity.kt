package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class TrailersEntity(
    @SerializedName("results") val results: ArrayList<VideoEntity>,
)