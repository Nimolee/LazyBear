package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class ReleaseDatesEntity(
    @SerializedName("results") val results: ArrayList<ReleaseDatesResultEntity>,
)

data class ReleaseDatesResultEntity(
    @SerializedName("iso_3166_1") val iso: String,
    @SerializedName("release_dates") val dates: ArrayList<ReleaseDatesCertificationEntity>,
)

data class ReleaseDatesCertificationEntity(
    @SerializedName("certification") val certification: String,
)