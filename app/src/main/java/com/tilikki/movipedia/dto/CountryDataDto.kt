package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.general.Country

data class CountryDataDto(
    @SerializedName("native_name") val name: String,
    @SerializedName("english_name") val englishName: String,
    @SerializedName("iso_3166_1") val iso_3166_1: String
) {
    fun toDomainCountry(): Country {
        return Country(name, iso_3166_1)
    }
}
