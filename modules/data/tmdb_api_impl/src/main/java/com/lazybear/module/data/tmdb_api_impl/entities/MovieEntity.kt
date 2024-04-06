package com.lazybear.module.data.tmdb_api_impl.entities

import app.lazybear.module.data.tmdb_api_impl.BuildConfig
import com.google.gson.annotations.SerializedName
import com.lazybear.module.data.tmdb_api.entities.CrewMember
import com.lazybear.module.data.tmdb_api.entities.Movie
import java.time.Duration
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

data class MovieEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("vote_average") val voteAverage: Float,
    @SerializedName("overview") val overview: String,
    @SerializedName("genres") val genres: ArrayList<GenreEntity>,
    @SerializedName("imdb_id") val imdbId: String?,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("production_countries") val productionCountries: ArrayList<ProductionCountryEntity>,
    @SerializedName("credits") val credits: CreditsEntity,
    @SerializedName("images") val images: ImagesEntity,
    @SerializedName("videos") val videos: TrailersEntity,
    @SerializedName("watch/providers") val watchProviders: WatchProvidersEntity,
)

private fun generateImageUrl(path: String): String {
    return "${BuildConfig.IMAGE_URL}$path"
}

fun MovieEntity.toDomain(): Movie {
    return Movie(
        id = id,
        title = title,
        originalTitle = originalTitle,
        tagline = tagline,
        voteAverage = voteAverage,
        overview = overview,
        genres = genres.map { it.toDomain() },
        imdbId = imdbId,
        poster = generateImageUrl(poster),
        backdrop = generateImageUrl(backdrop),
        releaseDate = LocalDate.parse(releaseDate, DateTimeFormatter.ISO_LOCAL_DATE),
        duration = Duration.of(runtime.toLong(), ChronoUnit.MINUTES),
        originalLanguage = originalLanguage,
        cast = credits.cast.map { it.toDomain() },
        crew = credits.crew.map { it.toDomain() }
            .filter { crewMemberJobIndex(it) != -1 }
            .sortedBy { crewMemberJobIndex(it) },
        backdrops = images.backdrops.map { it.toDomain() },
        trailers = videos.results.toDomain(),
        watchProviders = watchProviders.toDomain(),
        bad = originalLanguage == "ru" || productionCountries.any { it.countryCode == "RU" }
    )
}


//TODO: Check possibility to redo it in proper way
private fun crewMemberJobIndex(member: CrewMember): Int {
    return when (member.job) {
        "Producer" -> 0
        "Director" -> 1
        "Writer" -> 2
        else -> -1
    }
}