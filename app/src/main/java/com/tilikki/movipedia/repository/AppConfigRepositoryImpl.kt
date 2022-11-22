package com.tilikki.movipedia.repository

import android.content.Context
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tilikki.movipedia.dto.CountryDataDto
import com.tilikki.movipedia.dto.LanguageDto
import com.tilikki.movipedia.dto.TmdbConfigDto
import com.tilikki.movipedia.network.RetrofitInstance
import com.tilikki.movipedia.repository.endpoint.AppConfigInterface
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.reflect.Type

class AppConfigRepositoryImpl(private val context: Context) : AppConfigRepository {
    private val retrofit = RetrofitInstance.getCachedRetrofitInstance(context)
    private val appConfigInterface = retrofit.create(AppConfigInterface::class.java)

    override fun getAppConfig(): Single<TmdbConfigDto> {
        return appConfigInterface.getConfiguration()
    }

    override fun getLocalAppConfig(): TmdbConfigDto {
        val gson = Gson()
        val sharedPreferences =
            context.getSharedPreferences("TMDB_CONFIGURATION", Context.MODE_PRIVATE)

        val imageBaseUrl =
            sharedPreferences.getString("image_base_url", AppConfigRepository.baseUrl)
        val backdropSizes = sharedPreferences.getString("backdrop_sizes", "")
        val posterSizes = sharedPreferences.getString("poster_sizes", "")
        val logoSizes = sharedPreferences.getString("logo_sizes", "")
        val profileSizes = sharedPreferences.getString("profile_sizes", "")
        val stillSizes = sharedPreferences.getString("still_sizes", "")
        val changeKeys = sharedPreferences.getString("change_keys", "")

        val type: Type = object : TypeToken<ArrayList<String>>() {}.type

        return TmdbConfigDto(
            images = TmdbConfigDto.ImageConfig(
                baseUrl = imageBaseUrl ?: AppConfigRepository.baseUrl,
                secureBaseUrl = imageBaseUrl ?: AppConfigRepository.baseUrl,
                backdropSizes = gson.fromJson(backdropSizes, type),
                posterSizes = gson.fromJson(posterSizes, type),
                logoSizes = gson.fromJson(logoSizes, type),
                profileSizes = gson.fromJson(profileSizes, type),
                stillSizes = gson.fromJson(stillSizes, type),
            ),
            changeKeys = gson.fromJson(changeKeys, type),
        )
    }

    override fun setLocalAppConfig(tmdbConfigDto: TmdbConfigDto) {
        val gson = Gson()
        val sharedPreferences =
            context.getSharedPreferences("TMDB_CONFIGURATION", Context.MODE_PRIVATE)
        sharedPreferences.edit {
            putString("image_base_url", tmdbConfigDto.images.secureBaseUrl)
            putString("backdrop_sizes", gson.toJson(tmdbConfigDto.images.backdropSizes))
            putString("poster_sizes", gson.toJson(tmdbConfigDto.images.posterSizes))
            putString("logo_sizes", gson.toJson(tmdbConfigDto.images.logoSizes))
            putString("profile_sizes", gson.toJson(tmdbConfigDto.images.profileSizes))
            putString("still_sizes", gson.toJson(tmdbConfigDto.images.stillSizes))
            putString("change_keys", gson.toJson(tmdbConfigDto.changeKeys))
        }
    }

    override fun getCountriesList(): Observable<List<CountryDataDto>> {
        return appConfigInterface.getCountriesList()
    }

    override fun getLanguagesList(): Observable<List<LanguageDto>> {
        return appConfigInterface.getLanguagesList()
    }

    override fun getTranslationsList(): Observable<List<String>> {
        return appConfigInterface.getTranslations()
    }
}