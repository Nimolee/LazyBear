package com.lazybear.module.data.tmdb_api.errors

/**
 * Errors that may be achieved and should be handled during genres request
 */
sealed class GenresErrors {

    data object NetworkError : GenresErrors()

    data object UnknownError : GenresErrors()
}
