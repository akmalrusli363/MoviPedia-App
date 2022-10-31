package com.tilikki.movipedia.dto

import com.google.gson.annotations.SerializedName
import com.tilikki.movipedia.model.ProductionCompany

data class ProductionCompanyDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("logo_path") val logoPath: String?,
    @SerializedName("origin_country") val originCountry: String?,
) {
    fun toDomainProductionCompany(): ProductionCompany {
        return ProductionCompany(
            id = id,
            name = name,
            originCountryCode = originCountry.orEmpty(),
            logoPath = logoPath.orEmpty()
        )
    }
}
