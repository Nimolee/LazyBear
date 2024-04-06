package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.WatchProvider

data class WatchProvidersEntity(
    @SerializedName("results") val results: Map<String, WatchProvidersCountryEntity>,
)

data class WatchProvidersCountryEntity(
    @SerializedName("buy") val buyProviders: ArrayList<ProviderEntity>,
    @SerializedName("rent") val rentProviders: ArrayList<ProviderEntity>,
)


data class ProviderEntity(
    @SerializedName("logo_path") val logoPath: String,
    @SerializedName("provider_name") val name: String,
    @SerializedName("provider_id") val providerId: Int,
)

fun WatchProvidersEntity.toDomain(): List<WatchProvider> {
    val providers = mutableListOf<WatchProvider>()
    if (!results.containsKey("UA")) return emptyList()
    results["UA"]!!.buyProviders.forEach { provider ->
        providers.add(
            WatchProvider(
                id = provider.providerId,
                name = provider.name,
                logoLink = "${BuildConfig.IMAGE_URL}${provider.logoPath}",
                allowBuy = true,
                allowRent = results["UA"]!!.rentProviders.any { it.providerId == provider.providerId }
            ),
        )
    }
    results["UA"]!!.rentProviders.forEach { provider ->
        if (providers.none { it.id == provider.providerId }) {
            providers.add(
                WatchProvider(
                    id = provider.providerId,
                    name = provider.name,
                    logoLink = "${BuildConfig.IMAGE_URL}${provider.logoPath}",
                    allowBuy = false,
                    allowRent = true,
                )
            )
        }
    }
    return providers.sortedBy { it.id }
}