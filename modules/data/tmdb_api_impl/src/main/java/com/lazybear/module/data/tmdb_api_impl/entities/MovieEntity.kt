package com.lazybear.module.data.tmdb_api_impl.entities

import com.google.gson.annotations.SerializedName
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
    @SerializedName("imdb_id") val imdbId: String,
    @SerializedName("poster_path") val poster: String,
    @SerializedName("backdrop_path") val backdrop: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("runtime") val runtime: Int,
    @SerializedName("original_language") val originalLanguage: String,
)

private fun generateImageUrl(path: String): String {
    return "https://image.tmdb.org/t/p/original$path"
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
    )
}