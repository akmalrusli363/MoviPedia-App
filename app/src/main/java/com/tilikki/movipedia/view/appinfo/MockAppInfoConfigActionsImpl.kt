package com.tilikki.movipedia.view.appinfo

import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language

class MockAppInfoConfigActionsImpl : AppInfoConfigActions {
    override fun onChangeAppTheme(isDark: Boolean) {
        //
    }

    override fun onChangeTmdbLanguage(selectedLanguage: Language) {
        //
    }

    override fun onChangeTmdbRegion(selectedRegion: Country) {
        //
    }
}