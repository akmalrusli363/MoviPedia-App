package com.tilikki.movipedia.util

import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import java.util.*

fun generateLoremIpsumString(words: Int): String {
    return LoremIpsum(words).values.joinToString(" ").replace("\n", " ")
}

fun generateCountryFlagEmoji(countryCode: String): String {
    val countryCodeStr = countryCode.uppercase(Locale.ROOT)
    val firstLetter = Character.codePointAt(countryCodeStr, 0) - 0x41 + 0x1F1E6
    val secondLetter = Character.codePointAt(countryCodeStr, 1) - 0x41 + 0x1F1E6
    return String(Character.toChars(firstLetter)) + String(Character.toChars(secondLetter))
}

fun Locale.getCountryFlagEmoji(): String {
    return generateCountryFlagEmoji(country)
}
