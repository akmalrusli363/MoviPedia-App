package com.tilikki.movipedia.repository

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tilikki.movipedia.MovipediaApp
import com.tilikki.movipedia.dto.TmdbConfigDto
import com.tilikki.movipedia.network.RetrofitInstance
import io.reactivex.Single
import java.lang.reflect.Type

class AppConfigRepositoryImpl(context: Context) : AppConfigRepository {
    private val retrofit = RetrofitInstance.getCachedRetrofitInstance(MovipediaApp.appContext!!)
    private val appConfigInterface = retrofit.create(AppConfigInterface::class.java)

    override fun getAppConfig(): Single<TmdbConfigDto> {
        return appConfigInterface.getConfiguration()
    }

    override fun getLocalAppConfig(): TmdbConfigDto {
        val context = MovipediaApp.appContext!!
        val gson = Gson()
        val sharedPreferences =
            context.getSharedPreferences("TMDB Configuration", Context.MODE_PRIVATE)

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
}