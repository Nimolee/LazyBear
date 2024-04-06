package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class ProductionCountryEntity(
    @SerializedName("iso_3166_1") val countryCode: String,
)