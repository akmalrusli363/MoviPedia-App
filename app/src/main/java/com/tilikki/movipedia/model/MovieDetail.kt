package com.tilikki.movipedia.model

import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.repository.AppConfigRepository
import com.tilikki.movipedia.util.DateParser

data class MovieDetail(
    val id: Int,
    val title: String,
    val originalTitle: String,
    val releaseDate: String = "",
    val language: String = "en",
    val backdropPath: String = "",
    val posterPath: String = "",
    val overview: String = "",
    val status: String = "",
    val imdbID: String? = null,
    val tagline: String = "",
    val genres: List<Genre> = listOf(),
    val productionCompanies: List<ProductionCompany> = listOf(),
    val productionCountries: List<Country> = listOf(),
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

    fun generateBackdropPath(): String {
        return AppConfigRepository.baseUrl + AppConfigRepository.backdropSize + backdropPath
    }

    fun originalTitleEqualToTitle(): Boolean {
        return title != originalTitle
    }
}
