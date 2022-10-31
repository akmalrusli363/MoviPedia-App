package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName

data class LanguageDto(
    @SerializedName("name") val name: String,
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_639_1") val iso639_1: String,
)
