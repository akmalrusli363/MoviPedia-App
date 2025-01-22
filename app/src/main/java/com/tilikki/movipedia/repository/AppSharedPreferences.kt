package com.tilikki.movipedia.repository

import android.content.Context
import android.content.SharedPreferences
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language

class AppSharedPreferences(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences(APP_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    private val prefEditor = sharedPreferences.edit()

    companion object {
        const val APP_SHARED_PREFERENCES: String = "APP_SHARED_PREFERENCES"
        const val APP_THEME_MODE: String = "APP_THEME_MODE"
        const val TMDB_REGION: String = "TMDB_REGION"
        const val TMDB_LANGUAGE: String = "TMDB_LANGUAGE"

        const val TMDB_CONFIGURATION: String = "TMDB_CONFIGURATION"
    }

    fun isDarkMode(defaultFromSystem: Boolean): Boolean {
        return sharedPreferences.getBoolean(APP_THEME_MODE, defaultFromSystem)
    }

    fun switchThemeMode(darkMode: Boolean) {
        prefEditor.putBoolean(APP_THEME_MODE, darkMode).apply()
        prefEditor.apply()
    }

    fun setTmdbRegion(region: Country) {
        prefEditor.putString(TMDB_REGION, region.countryCode)
        prefEditor.apply()
    }

    fun getTmdbRegion(): String? {
        return sharedPreferences.getString(TMDB_REGION, null)
    }

    fun setTmdbLanguage(language: Language) {
        prefEditor.putString(TMDB_LANGUAGE, language.languageCode)
        prefEditor.apply()
    }

    fun getTmdbLanguage(): String {
        return sharedPreferences.getString(TMDB_LANGUAGE, Constants.DEFAULT_REQUEST_LANGUAGE)
            ?: Constants.DEFAULT_REQUEST_LANGUAGE
    }

    fun registerSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    fun unregisterSharedPreferenceChangeListener(listener: SharedPreferences.OnSharedPreferenceChangeListener) {
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }
}