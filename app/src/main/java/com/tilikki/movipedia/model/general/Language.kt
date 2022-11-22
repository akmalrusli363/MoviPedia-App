package com.tilikki.movipedia.model.general

import java.util.*

data class Language(
    val languageCode: String,
    val name: String,
) {
    companion object Defaults {
        val ENGLISH = Language("en-US", "English (United States)")

        fun getLanguageName(
            languageCode: String,
        ): String {
            return Locale.forLanguageTag(languageCode).displayName
        }

        fun fromLanguageCode(
            languageCode: String,
        ): Language {
            return Language(
                languageCode = languageCode,
                name = getLanguageName(languageCode)
            )
        }
    }
}
