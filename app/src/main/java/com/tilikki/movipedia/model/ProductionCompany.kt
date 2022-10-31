package com.tilikki.movipedia.model

data class ProductionCompany(
    val id: Int,
    val name: String,
    val originCountryCode: String = "",
    val logoPath: String = "",
)
