package com.tilikki.movipedia.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tilikki.movipedia.repository.AppSharedPreferences

class ThemeEngineViewModel(
    private val sharedPreferences: AppSharedPreferences
) : BaseDisposableViewModel() {
    private val _darkMode = MutableLiveData(sharedPreferences.isDarkMode(false))
    val darkMode: LiveData<Boolean> = _darkMode

    fun switchThemeMode(darkMode: Boolean) {
        sharedPreferences.switchThemeMode(darkMode)
        this._darkMode.value = darkMode
    }
}