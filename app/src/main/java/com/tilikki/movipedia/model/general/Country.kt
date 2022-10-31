package com.tilikki.movipedia.model.general

import com.tilikki.movipedia.util.generateCountryFlagEmoji

data class Country(
    val name: String,
    val countryCode: String
) {
    fun getCountryFlagEmoji(): String {
        return generateCountryFlagEmoji(countryCode)
    }
}
