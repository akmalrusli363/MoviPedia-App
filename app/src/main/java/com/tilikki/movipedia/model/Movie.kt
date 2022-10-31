package com.tilikki.movipedia.model

import com.tilikki.movipedia.repository.AppConfigRepository
import com.tilikki.movipedia.util.DateParser
import java.text.SimpleDateFormat

data class Movie(
    val id: Int,
    val title: String,
    val releaseDate: String,
    val language: String = "en",
    val backdropPath: String = "",
    val posterPath: String = "",
    val overview: String = "",
    val voteCount: Int = 0,
    val voteAverage: Double = 0.0,
) {
    fun generatePosterPath(): String {
        return AppConfigRepository.baseUrl + AppConfigRepository.posterSize + posterPath
    }

    fun formatReleaseDate(outputDateFormat: String): String {
        return DateParser.convertDateFormat(releaseDate, "yyyy-MM-dd", outputDateFormat)
    }

    fun formatReleaseDate(outputDateStyle: Int): String {
        return DateParser.convertDateFormat(releaseDate, "yyyy-MM-dd", outputDateStyle)
    }
}
