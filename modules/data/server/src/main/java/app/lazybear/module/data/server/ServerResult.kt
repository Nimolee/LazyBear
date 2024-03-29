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
    class NetworkError<T : Any> : ServerResult<T>() {
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
    class UnknownError<T : Any> : ServerResult<T>() {
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