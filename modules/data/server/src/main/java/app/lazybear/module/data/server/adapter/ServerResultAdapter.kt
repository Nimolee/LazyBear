package app.lazybear.module.data.server.adapter

import app.lazybear.module.data.server.ServerResult
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ServerResultAdapter<S : Any>(
    private val successType: Type,
) : CallAdapter<S, Call<ServerResult<S>>> {

    override fun adapt(call: Call<S>): Call<ServerResult<S>> {
        return ServerResultCallAdapter(call)
    }

    override fun responseType(): Type = successType
}