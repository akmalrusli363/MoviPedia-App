package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.general.Country

data class CountryDto(
    @SerializedName("name") val name: String,
    @SerializedName("iso_3166_1") val iso_3166_1: String
) {
    fun toDomainCountry(): Country {
        return Country(name, iso_3166_1)
    }
}
