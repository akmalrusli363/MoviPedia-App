package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.MovieDetail

data class MovieDetailDto(
    @SerializedName("id") val id: Int,
    @SerializedName("imdb_id") val imdbID: String,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("status") val status: String,
    @SerializedName("original_title") val originalTitle: String,
    @SerializedName("tagline") val tagline: String,
    @SerializedName("homepage") val homepage: String,
    @SerializedName("genres") val genres: List<GenreDto>,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("original_language") val language: String,
    @SerializedName("production_companies") val productionCompanies: List<ProductionCompanyDto>,
    @SerializedName("production_countries") val productionCountries: List<CountryDto>,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
) {
    fun toDomainMovieDetail(): MovieDetail {
        return MovieDetail(
            id = id,
            title = title,
            originalTitle = originalTitle,
            releaseDate = releaseDate.orEmpty(),
            language = language,
            backdropPath = backdropPath.orEmpty(),
            posterPath = posterPath.orEmpty(),
            overview = overview,
            status = status,
            tagline = tagline,
            imdbID = imdbID,
            genres = genres.map(GenreDto::toDomainGenre),
            productionCompanies = productionCompanies.map(ProductionCompanyDto::toDomainProductionCompany),
            productionCountries = productionCountries.map(CountryDto::toDomainCountry),
            voteCount = voteCount,
            voteAverage = voteAverage,
        )
    }
}