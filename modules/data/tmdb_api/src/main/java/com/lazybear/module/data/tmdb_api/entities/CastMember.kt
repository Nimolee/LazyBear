package com.lazybear.module.data.tmdb_api.entities

data class CastMember(
    val id: Int,
    val name: String,
    val profileUrl: String?,
    val character: String,
)
