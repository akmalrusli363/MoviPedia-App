package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.Movie

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overview: String,
    @SerializedName("original_language") val language: String,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("adult") val adult: Boolean,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int,
) {
    fun toDomainMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            releaseDate = releaseDate.orEmpty(),
            language = language,
            backdropPath = backdropPath.orEmpty(),
            posterPath = posterPath.orEmpty(),
            overview = overview,
            voteCount = voteCount,
            voteAverage = voteAverage,
        )
    }
}