package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.Keyword

data class KeywordListDto(
    @SerializedName("id") val id: Int,
    @SerializedName("keywords") val keywords: List<KeywordDto>,
) {
    fun toDomainKeywordList(): List<Keyword> {
        return keywords.map(KeywordDto::toDomainKeyword)
    }
}
