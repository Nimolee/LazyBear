package app.lazybear.module.data.server

/**
 * Sealed class to encapsulate server operation result.
 */
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
                Result
                throw IllegalAccessException()
            }
    }

    /**
     * Plain error with code and error message.
     * Used to handle errors with codes 400..422
     * Contain error code and error string for future operation.
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
     * Error that represent problem in network connection.
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
     * Unexpected exceptions that are not handled by the application.
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

/**
 * Returns a ServerResult containing the result of applying transform function to body
 * when result is [ServerResult.Success] otherwise error will be returned without modification.
 */
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

/**
 * Execute following function when this result is success.
 */
suspend fun <T> ServerResult<T>.onSuccess(
    onSuccess: suspend (body: T?) -> Unit,
): ServerResult<T> {
    if (this is ServerResult.Success) {
        onSuccess(this.body)
    }
    return this
}

/**
 * Extension to simplify handling of every error type.
 */
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

/**
 * During consecutive network requests where one request goes after another
 * this method may be used to remove included results.
 * Because next network request should not be executed when previous fail conversion
 * will return success body of second result or errors from both result.
 */
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

/**
 * This method should be used in case when nullable type returned after [mergeErrors] method but
 * non nullable expected.
 * If [ServerResult] with null body provided [UnknownError] will be returned.
 */
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

/**
 * Convert [ServerResult] to [Result] to be used inside ViewModels.
 */
fun <T, E> ServerResult<T>.toResult(
    errorMapper: (ServerResult.Error) -> E,
    networkErrorMapper: () -> E,
    unknownErrorMapper: () -> E,
): Result<T, E> {
    return when (this) {
        is ServerResult.Error -> Result(null, errorMapper(this))
        ServerResult.NetworkError -> Result(null, networkErrorMapper())
        is ServerResult.Success -> Result(this.body!!, null)
        ServerResult.UnknownError -> Result(null, unknownErrorMapper())
    }
}