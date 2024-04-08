package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.Provider
import com.lazybear.module.data.tmdb_api.entities.WatchProvider

data class WatchProvidersEntity(
    @SerializedName("results") val results: Map<String, WatchProvidersCountryEntity>,
)

data class WatchProvidersCountryEntity(
    @SerializedName("buy") val buyProviders: ArrayList<ProviderEntity>?,
    @SerializedName("rent") val rentProviders: ArrayList<ProviderEntity>?,
)


data class ProviderEntity(
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_name") val name: String,
    @SerializedName("provider_id") val providerId: Int,
)

fun WatchProvidersEntity.toDomain(): List<WatchProvider> {
    val providers = mutableListOf<WatchProvider>()
    results["UA"]?.buyProviders?.forEach { providerEntity ->
        providerFromId(providerEntity.providerId)?.let { provider ->
            providers.add(
                WatchProvider(
                    id = providerEntity.providerId,
                    provider = provider,
                    name = providerEntity.name,
                    logoLink = "${BuildConfig.IMAGE_URL}${providerEntity.logoPath}",
                    allowBuy = true,
                    allowRent = results["UA"]?.rentProviders?.any { it.providerId == providerEntity.providerId }
                        ?: false
                ),
            )
        }
    }
    results["UA"]?.rentProviders?.forEach { providerEntity ->
        if (providers.none { it.id == providerEntity.providerId }) {
            providerFromId(providerEntity.providerId)?.let { provider ->
                providers.add(
                    WatchProvider(
                        id = providerEntity.providerId,
                        provider = provider,
                        name = providerEntity.name,
                        logoLink = "${BuildConfig.IMAGE_URL}${providerEntity.logoPath}",
                        allowBuy = false,
                        allowRent = true,
                    )
                )
            }
        }
    }
    return providers.sortedBy { it.id }
}

private fun providerFromId(id: Int): Provider? {
    return when (id) {
        2 -> Provider.AppleTV
        3 -> Provider.GooglePlayMovies
        8 -> Provider.Netflix
        35 -> Provider.RakutenTV
        192 -> Provider.YouTube
        283 -> Provider.Crunchyroll
        384 -> Provider.HBOMax
        else -> null
    }
}