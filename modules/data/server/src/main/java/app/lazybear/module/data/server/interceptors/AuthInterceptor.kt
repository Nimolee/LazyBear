package app.lazybear.module.data.server.interceptors

import app.lazybear.module.utils.log.logD
import kotlinx.coroutines.flow.StateFlow
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val _bearerFlow: StateFlow<String?>,
    private val _apiKey: String,
) : Interceptor {
    companion object {
        private const val TAG = "AuthInterceptor"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val token = _bearerFlow.value
        val request = chain.request().newBuilder()
        if (!token.isNullOrEmpty()) {
            logD(TAG, "intercept") { "Add Bearer token $token" }
            request.addHeader("Authorization", "Bearer $token")
        } else {
            logD(TAG, "intercept") { "No bearer token use api_key instead" }
            val newUrl = chain.request()
                .url
                .newBuilder()
                .addQueryParameter("api_key", _apiKey)
                .build()
            logD(TAG, "intercept") { "New URL = $newUrl" }
            request.url(newUrl)
        }
        return chain.proceed(request.build())
    }
}