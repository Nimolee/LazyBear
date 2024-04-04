package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.CrewMember

data class CrewMemberEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("job") val job: String,
    @SerializedName("department") val department: String,
)

private fun generateImageUrl(path: String): String {
    return "${BuildConfig.IMAGE_URL}$path"
}

fun CrewMemberEntity.toDomain(): CrewMember {
    return CrewMember(
        id = id,
        name = name,
        profile = profilePath?.let { generateImageUrl(it) },
        job = job,
        department = department,
    )
}