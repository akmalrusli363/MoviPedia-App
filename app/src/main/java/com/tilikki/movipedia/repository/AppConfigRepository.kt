package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.CountryDataDto
import com.tilikki.movipedia.dto.LanguageDto
import com.tilikki.movipedia.dto.TmdbConfigDto
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

    fun getCountriesList(): Observable<List<CountryDataDto>>
    fun getLanguagesList(): Observable<List<LanguageDto>>
    fun getTranslationsList(): Observable<List<String>>
}