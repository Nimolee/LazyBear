package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class MovieIdEntity(
    @SerializedName("id") val id: Int,
)