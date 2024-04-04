package com.lazybear.module.data.tmdb_api.entities

data class CrewMember(
    val id: Int,
    val name: String,
    val profile: String?,
    val job: String,
    val department: String,
)
