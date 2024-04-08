package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName

data class KeywordsEntity(
    @SerializedName("keywords") val keywords: ArrayList<KeywordEntity>,
)