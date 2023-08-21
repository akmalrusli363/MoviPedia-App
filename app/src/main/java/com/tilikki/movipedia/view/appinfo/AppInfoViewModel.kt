package com.tilikki.movipedia.view.appinfo

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.text.intl.Locale
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language
import com.tilikki.movipedia.repository.AppConfigRepository
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.util.swapList
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AppInfoViewModel(
    private val appConfigRepo: AppConfigRepository,
    private val sharedPreferences: AppSharedPreferences
) : BaseDisposableViewModel() {
    val countryList: MutableList<Country> = mutableStateListOf()
    val languageList: MutableList<Language> = mutableStateListOf(getTmdbLanguage())

    fun setTmdbLanguage(language: Language) {
        sharedPreferences.setTmdbLanguage(language)
    }

    fun getTmdbLanguage(): Language {
        val storedLanguage = sharedPreferences.getTmdbLanguage()
        return Language.fromLanguageCode(storedLanguage)
    }

    fun setTmdbRegion(country: Country) {
        sharedPreferences.setTmdbRegion(country)
    }

    fun getTmdbRegion(): Country {
        val storedCountry = sharedPreferences.getTmdbRegion() ?: getDefaultRegionCodeFromDevice()
        return Country.fromCountryCode(storedCountry)
    }

    private fun getDefaultRegionCodeFromDevice(): String {
        return Locale.current.region
    }

    fun fetchConfig() {
        fetchCountryList()
        fetchLocalisationList()
    }

    private fun fetchCountryList() {
        val disposableFetchCountriesList = appConfigRepo.getCountriesList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ list ->
                Log.d("MvFetcher", list.toString())
                countryList.swapList(list)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
        compositeDisposable.addAll(disposableFetchCountriesList)
    }

    private fun fetchLocalisationList() {
        val disposableFetchLanguagesList = appConfigRepo.getTranslationsList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ dto ->
                val domainList = dto.map { Language.fromLanguageCode(it) }
                Log.d("MvFetcher", domainList.toString())
                languageList.swapList(domainList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
        compositeDisposable.addAll(disposableFetchLanguagesList)
    }
}

