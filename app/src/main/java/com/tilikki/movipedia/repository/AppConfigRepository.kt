package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.TmdbConfigDto
import com.tilikki.movipedia.model.general.Country
import com.tilikki.movipedia.model.general.Language
import io.reactivex.Observable
import io.reactivex.Single

interface AppConfigRepository {
    companion object {
        const val baseUrl = "https://image.tmdb.org/t/p/"
        const val backdropSize = "w780"
        const val posterSize = "w500"
    }

    fun getAppConfig(): Single<TmdbConfigDto>

    fun getLocalAppConfig(): TmdbConfigDto
    fun setLocalAppConfig(tmdbConfigDto: TmdbConfigDto)

    fun getCountriesList(): Observable<List<Country>>
    fun getLanguagesList(): Observable<List<Language>>
    fun getTranslationsList(): Observable<List<String>>
}