package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName

data class CountryDto(
    @SerializedName("name") val name: String,
    @SerializedName("iso_3166_1") val iso_3166_1: String
)
