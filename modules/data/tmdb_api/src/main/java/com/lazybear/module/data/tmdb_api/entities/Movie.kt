package com.lazybear.module.data.tmdb_api.entities

import java.time.Duration
import java.time.LocalDate

data class Movie(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val tagline: String,
    val voteAverage: Float,
    val overview: String,
    val genres: List<Genre>,
    val imdbId: String?,
    val poster: String,
    val backdrop: String,
    val releaseDate: LocalDate,
    val duration: Duration,
    val originalLanguage: String,
    val certification: String,
    val crew: List<CrewMember>,
    val cast: List<CastMember>,
    val backdrops: List<MovieImage>,
    val trailers: List<MovieVideo>,
    val watchProviders: List<WatchProvider>,
    val bad: Boolean,
    val keywords: List<Keyword>,
)