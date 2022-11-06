package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.Genre

data class GenreListDto(
    @SerializedName("genres") val genres: List<GenreDto>
) {
    fun toDomainGenreList(): List<Genre> {
        return genres.map { genre ->
            genre.toDomainGenre()
        }
    }
}
