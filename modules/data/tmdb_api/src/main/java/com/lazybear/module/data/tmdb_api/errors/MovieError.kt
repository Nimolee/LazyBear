package com.lazybear.module.data.tmdb_api.errors

/**
 * Errors that may be achieved and should be handled during movie request
 */
sealed class MovieError {
    data object NetworkError : MovieError()

    data object UnknownError : MovieError()
}