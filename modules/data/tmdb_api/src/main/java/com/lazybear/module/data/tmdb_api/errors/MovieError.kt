package com.lazybear.module.data.tmdb_api.errors

sealed class MovieError {
    data object NetworkError : MovieError()

    data object UnknownError : MovieError()
}