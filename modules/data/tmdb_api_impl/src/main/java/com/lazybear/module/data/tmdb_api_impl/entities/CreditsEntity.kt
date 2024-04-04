package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class CreditsEntity(
    @SerializedName("cast") val cast: ArrayList<CastMemberEntity>,
    @SerializedName("crew") val crew: ArrayList<CrewMemberEntity>,
)