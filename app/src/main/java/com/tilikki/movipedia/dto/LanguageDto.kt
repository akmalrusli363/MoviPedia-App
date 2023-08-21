package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.db.entity.EntityLanguage
import com.tilikki.movipedia.model.general.Language

data class LanguageDto(
    @SerializedName("name") val name: String,
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso639_1: String,
) {
    fun toEntityLanguage(): EntityLanguage {
        val languageName = if (name.isNotBlank()) "$englishName ($name)" else englishName
        return EntityLanguage(
            languageCode = iso639_1,
            name = languageName,
        )
    }

    fun toDomainLanguage(): Language {
        val languageName = if (name.isNotBlank()) "$englishName ($name)" else englishName
        return Language(
            languageCode = iso639_1,
            name = languageName,
        )
    }
}
