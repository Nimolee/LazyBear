package app.lazybear.module.data.server

sealed class ServerResult<out T : Any?> {
    abstract val body: T?
    abstract val error: String

    val isSuccess: Boolean
        get() = this is Success<*>

    /**
     * Success data response
     */
    data class Success<T : Any>(
        override val body: T?,
    ) : ServerResult<T>() {
        override val error: Nothing
            get() {
                throw IllegalAccessException()
            }
    }

    /**
     * Plain error with code and error message
     */
    data class Error(
        val code: Int,
        override val error: String,
    ) : ServerResult<Nothing>() {
        override val body: Nothing
            get() {
                throw IllegalAccessException()
            }
    }

    /**
     * Network connection error
     */
    data object NetworkError : ServerResult<Nothing>() {
        override val body: Nothing
            get() {
                throw IllegalAccessException()
            }
        override val error: Nothing
            get() {
                throw IllegalAccessException()
            }
    }


    /**
     * Unexpected exceptions
     */
    data object UnknownError : ServerResult<Nothing>() {
        override val body: Nothing
            get() {
                throw IllegalAccessException()
            }
        override val error: Nothing
            get() {
                throw IllegalAccessException()
            }
    }
}

suspend fun <T, M : Any> ServerResult<T>.map(
    mapper: suspend (body: T?) -> M?,
): ServerResult<M?> {
    return when (this) {
        is ServerResult.Success -> ServerResult.Success(mapper(body))
        is ServerResult.Error -> this
        is ServerResult.NetworkError -> this
        is ServerResult.UnknownError -> this
    }
}

suspend fun <T> ServerResult<T>.onSuccess(
    onSuccess: suspend (body: T?) -> Unit,
): ServerResult<T> {
    if (this is ServerResult.Success) {
        onSuccess(this.body)
    }
    return this
}

suspend fun <T> ServerResult<T>.onError(
    onError: (code: Int, error: String) -> Unit,
    onUnknownError: () -> Unit,
    onNetworkError: () -> Unit,
): ServerResult<T> {
    when (val error = this) {
        is ServerResult.Error -> onError(error.code, error.error)
        ServerResult.NetworkError -> onNetworkError()
        ServerResult.UnknownError -> onUnknownError()
        is ServerResult.Success -> Unit
    }
    return this
}

fun <T> ServerResult<ServerResult<T>?>.mergeErrors(): ServerResult<T> {
    return when (this) {
        is ServerResult.Error -> this
        ServerResult.NetworkError -> ServerResult.NetworkError
        ServerResult.UnknownError -> ServerResult.UnknownError
        is ServerResult.Success -> when (val body = this.body) {
            is ServerResult.Success -> body
            is ServerResult.Error -> body
            ServerResult.NetworkError -> ServerResult.NetworkError
            ServerResult.UnknownError -> ServerResult.UnknownError
            null -> ServerResult.UnknownError
        }
    }
}

fun <T> ServerResult<T?>.dropNull(): ServerResult<T> {
    return when (this) {
        is ServerResult.Success -> {
            if (body != null) {
                ServerResult.Success(body!!)
            } else {
                ServerResult.UnknownError
            }
        }

        is ServerResult.Error -> this
        ServerResult.NetworkError -> ServerResult.NetworkError
        ServerResult.UnknownError -> ServerResult.UnknownError
    }
}