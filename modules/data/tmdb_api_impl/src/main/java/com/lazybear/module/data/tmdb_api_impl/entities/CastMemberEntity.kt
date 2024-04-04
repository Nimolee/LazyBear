package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.CastMember

data class CastMemberEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("character") val character: String,
)

private fun generateImageUrl(path: String): String {
    return "${BuildConfig.IMAGE_URL}$path"
}

fun CastMemberEntity.toDomain(): CastMember {
    return CastMember(
        id = id,
        name = name,
        profileUrl = profilePath?.let { generateImageUrl(it) },
        character = character,
    )
}
