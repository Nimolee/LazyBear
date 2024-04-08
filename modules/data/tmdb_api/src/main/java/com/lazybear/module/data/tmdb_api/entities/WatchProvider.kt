package com.lazybear.module.data.tmdb_api.entities

data class WatchProvider(
    val id: Int,
    val provider: Provider,
    val name: String,
    val logoLink: String,
    val allowBuy: Boolean,
    val allowRent: Boolean,
)