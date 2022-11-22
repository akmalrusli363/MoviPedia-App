package com.tilikki.movipedia.model.general

import com.tilikki.movipedia.util.generateCountryFlagEmoji
import java.util.*

data class Country(
    val name: String,
    val countryCode: String
) {
    fun getCountryFlagEmoji(): String {
        return generateCountryFlagEmoji(countryCode)
    }

    companion object {
        fun fromCountryCode(countryCode: String): Country {
            val locale = Locale("", countryCode)
            return Country(locale.displayCountry, countryCode)
        }
    }
}
