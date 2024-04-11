package com.lazybear.module.data.tmdb_api.errors

sealed class RecommendError {
    data object NoResults : RecommendError()

    data object NetworkError : RecommendError()

    data object UnknownError : RecommendError()
}