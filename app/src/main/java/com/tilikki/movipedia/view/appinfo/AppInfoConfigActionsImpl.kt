package com.tilikki.movipedia.view.appinfo

import android.util.Log
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language
import com.tilikki.movipedia.view.ThemeEngineViewModel

class AppInfoConfigActionsImpl(
    private val viewModel: AppInfoViewModel,
    private val themeEngineViewModel: ThemeEngineViewModel
) : AppInfoConfigActions {
    override fun onChangeAppTheme(isDark: Boolean) {
        themeEngineViewModel.switchThemeMode(isDark)
        Log.d("thChanger", "changed theme mode to ${if (isDark) "dark" else "light"} theme")
    }

    override fun onChangeTmdbLanguage(selectedLanguage: Language) {
        viewModel.setTmdbLanguage(selectedLanguage)
    }

    override fun onChangeTmdbRegion(selectedRegion: Country) {
        viewModel.setTmdbRegion(selectedRegion)
    }
}