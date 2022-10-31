package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.TmdbConfigDto
import io.reactivex.Single
import retrofit2.http.GET

interface AppConfigInterface {
    @GET("/configuration")
    fun getConfiguration(): Single<TmdbConfigDto>
}