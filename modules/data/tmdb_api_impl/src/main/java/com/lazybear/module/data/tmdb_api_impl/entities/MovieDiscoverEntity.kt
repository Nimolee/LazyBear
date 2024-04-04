package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class MovieDiscoverEntity(
    @SerializedName("total_results") val totalResults: Int,
    @SerializedName("results") val results: ArrayList<MovieIdEntity>,
)