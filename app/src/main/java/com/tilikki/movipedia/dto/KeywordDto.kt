package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.Keyword

data class KeywordDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
) {
    fun toDomainKeyword(): Keyword {
        return Keyword(
            id = id,
            name = name
        )
    }
}
