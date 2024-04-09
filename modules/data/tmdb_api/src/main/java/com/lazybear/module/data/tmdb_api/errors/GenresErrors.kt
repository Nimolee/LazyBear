package com.lazybear.module.data.tmdb_api.errors

sealed class GenresErrors {

    data object NetworkError : GenresErrors()

    data object UnknownError : GenresErrors()
}
