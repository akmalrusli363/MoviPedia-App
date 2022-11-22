package com.tilikki.movipedia.view.appinfo

import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language

interface AppInfoConfigActions {
    fun onChangeAppTheme(isDark: Boolean)
    fun onChangeTmdbLanguage(selectedLanguage: Language)
    fun onChangeTmdbRegion(selectedRegion: Country)
}