package com.tilikki.movipedia.view.appinfo

import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language

class AppInfoConfigActionsImpl(private val viewModel: AppInfoViewModel) : AppInfoConfigActions {
    override fun onChangeAppTheme(isDark: Boolean) {
        viewModel.switchThemeMode(isDark)
    }

    override fun onChangeTmdbLanguage(selectedLanguage: Language) {
        viewModel.setTmdbLanguage(selectedLanguage)
    }

    override fun onChangeTmdbRegion(selectedRegion: Country) {
        viewModel.setTmdbRegion(selectedRegion)
    }
}