package app.lazybear.module.data.server.adapter

import app.lazybear.module.data.server.ServerResult
import app.lazybear.module.utils.log.logD
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Response.success
import java.io.IOException

internal class ServerResultCallAdapter<S : Any?>(
    private val delegate: Call<S>,
) : Call<ServerResult<S>> {
    companion object {
        const val TAG = "ServerResultCallAdapter"
    }


    override fun enqueue(callback: Callback<ServerResult<S>>) {
        return delegate.enqueue(object : Callback<S> {
            override fun onResponse(call: Call<S>, response: Response<S>) {
                val body = response.body()
                val code = response.code()
                val error = response.errorBody()
                when (code) {
                    200 -> {
                        if (body != null) {
                            callback.onResponse(
                                this@ServerResultCallAdapter,
                                success(ServerResult.Success(body)),
                            )
                        } else {
                            callback.onResponse(
                                this@ServerResultCallAdapter,
                                success(ServerResult.Success(null)),
                            )
                        }
                    }

                    204 -> {
                        callback.onResponse(
                            this@ServerResultCallAdapter,
                            success(ServerResult.Success(null)),
                        )
                    }

                    in 400..422 -> {
                        if (error != null) {
                            callback.onResponse(
                                this@ServerResultCallAdapter,
                                success(
                                    ServerResult.Error(
                                        code = code,
                                        error = error.string(),
                                    )
                                ),
                            )
                        } else {
                            callback.onResponse(
                                this@ServerResultCallAdapter,
                                success(ServerResult.UnknownError),
                            )
                        }
                    }

                    else -> {
                        logD(
                            TAG,
                            "Unsupported response code",
                        ) {
                            "$code - $body - ${error?.string()}"
                        }
                        throw UnsupportedOperationException()
                    }
                }
            }

            override fun onFailure(call: Call<S>, throwable: Throwable) {
                val networkResponse: ServerResult<Nothing> = when (throwable) {
                    is IOException -> ServerResult.NetworkError
                    else -> ServerResult.UnknownError
                }
                callback.onResponse(this@ServerResultCallAdapter, success(networkResponse))
            }
        })
    }

    override fun isExecuted() = delegate.isExecuted

    override fun timeout(): Timeout = delegate.timeout()

    override fun clone() = ServerResultCallAdapter(delegate.clone())

    override fun isCanceled() = delegate.isCanceled

    override fun cancel() = delegate.cancel()

    override fun execute(): Response<ServerResult<S>> {
        throw UnsupportedOperationException("NetworkResponseCall doesn't support execute")
    }

    override fun request(): Request = delegate.request()
}