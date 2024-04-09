package app.lazybear.module.data.server

data class Result<out T, out E>(
    val body: T?,
    val error: E?,
) {
    val isSuccess: Boolean get() = error == null

    suspend fun onSuccess(action: suspend (T) -> Unit): Result<T, E> {
        if (body != null && error == null) {
            action(body)
        }
        return this
    }

    suspend fun onError(action: suspend (E) -> Unit): Result<T, E> {
        if (error != null) {
            action(error)
        }
        return this
    }
}