package com.tilikki.movipedia.view.appinfo

import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language
import java.util.*
import androidx.compose.ui.text.intl.Locale as ComposeLocale

data class AppInfoConfigData(
    val countryList: List<Country>,
    val languageList: List<Language>,
    val defaultCountry: Country = Country.fromCountryCode(ComposeLocale.current.region),
    val defaultLanguage: Language = Language.fromLanguageCode(Locale.US.language),
) {
    fun getDisplayLanguageName(language: Language): String {
        return Locale(language.languageCode).displayLanguage
    }

    fun getDisplayCountryName(country: Country): String {
        return Locale("", country.countryCode).displayCountry
    }
}
