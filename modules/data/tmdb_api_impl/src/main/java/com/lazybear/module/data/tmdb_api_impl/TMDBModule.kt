package com.lazybear.module.data.tmdb_api_impl

import app.lazybear.module.data.server.adapter.ServerResultAdapterFactory
import app.lazybear.module.data.server.interceptors.AuthInterceptor
import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.lazybear.module.data.tmdb_api.TMDBRepository
import com.lazybear.module.data.tmdb_api_impl.endpoints.DiscoverEndpoint
import com.lazybear.module.data.tmdb_api_impl.endpoints.GenresEndpoint
import com.lazybear.module.data.tmdb_api_impl.endpoints.MoviesEndpoint
import kotlinx.coroutines.flow.StateFlow
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private fun buildHttpClient(
    tokenFlow: StateFlow<String?>,
    apiKey: String,
): OkHttpClient {
    val httpClient = OkHttpClient.Builder()

    httpClient.callTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addInterceptor(AuthInterceptor(tokenFlow, apiKey))
    if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
    }
    return httpClient.build()
}

fun tmdbModule(
    tokenFlowName: String,
    apiKey: String,
) = module {
    single<Retrofit> {
        Retrofit.Builder().client(buildHttpClient(get(named(tokenFlowName)), apiKey))
            .baseUrl(BuildConfig.API_URL)
            .addCallAdapterFactory(ServerResultAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        get<Retrofit>().create(GenresEndpoint::class.java)
    }
    single {
        get<Retrofit>().create(DiscoverEndpoint::class.java)
    }
    single {
        get<Retrofit>().create(MoviesEndpoint::class.java)
    }
    single<TMDBRepository> {
        TMDBRepositoryImpl(get(), get(), get())
    }
}