package com.tilikki.movipedia.view.main

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.tilikki.movipedia.MovipediaApp
import com.tilikki.movipedia.dto.TmdbConfigDto
import com.tilikki.movipedia.repository.AppConfigRepository
import com.tilikki.movipedia.repository.AppConfigRepositoryImpl
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.schedulers.Schedulers

class AppInitializationViewModel : BaseDisposableViewModel() {
    private val repository: AppConfigRepository = AppConfigRepositoryImpl(MovipediaApp.appContext!!)

    fun getConfiguration() {
        val disposable = repository.getAppConfig()
            .subscribeOn(Schedulers.io())
            .subscribe({ conf ->
                Log.d("Configg", conf.toString())
                passToSharedPreferences(conf)
            }, { err ->
                Log.e("Configg", err.message, err)
            })
        compositeDisposable.addAll(disposable)
    }

    private fun passToSharedPreferences(config: TmdbConfigDto) {
        val gson = Gson()
        MovipediaApp.appContext?.let {
            val sharedPreferences = it.getSharedPreferences("TMDB Configuration", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("image_base_url", config.images.secureBaseUrl)
            editor.putString("backdrop_sizes", gson.toJson(config.images.backdropSizes))
            editor.putString("poster_sizes", gson.toJson(config.images.posterSizes))
            editor.putString("logo_sizes", gson.toJson(config.images.logoSizes))
            editor.putString("profile_sizes", gson.toJson(config.images.profileSizes))
            editor.putString("still_sizes", gson.toJson(config.images.stillSizes))
            editor.putString("change_keys", gson.toJson(config.changeKeys))
            editor.apply()
        }
    }
}