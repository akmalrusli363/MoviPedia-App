package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.Genre

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) {
    fun toDomainGenre(): Genre {
        return Genre(
            id = id,
            name = name
        )
    }
}
