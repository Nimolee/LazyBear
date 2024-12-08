package com.lazybear.module.data.tmdb_api.errors


/**
 * Errors that may be achieved and should be handled during recommendation sequence
 */
sealed class RecommendError {
    /**
     * No result was found. Filters too strict.
     */
    data object NoResults : RecommendError()

    data object NetworkError : RecommendError()

    data object UnknownError : RecommendError()
}