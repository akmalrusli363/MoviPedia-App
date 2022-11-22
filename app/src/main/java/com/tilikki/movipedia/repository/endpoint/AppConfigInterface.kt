package com.tilikki.movipedia.repository.endpoint

import com.tilikki.movipedia.dto.CountryDataDto
import com.tilikki.movipedia.dto.LanguageDto
import com.tilikki.movipedia.dto.TmdbConfigDto
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET

interface AppConfigInterface {
    @GET("configuration")
    fun getConfiguration(): Single<TmdbConfigDto>

    @GET("configuration/languages")
    fun getLanguagesList(): Observable<List<LanguageDto>>

    @GET("configuration/countries")
    fun getCountriesList(): Observable<List<CountryDataDto>>

    @GET("configuration/primary_translations")
    fun getTranslations(): Observable<List<String>>
}