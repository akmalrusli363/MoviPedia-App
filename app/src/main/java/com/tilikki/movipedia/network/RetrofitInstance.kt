package com.tilikki.movipedia.network

import android.content.Context
import com.tilikki.movipedia.util.TimeInterval
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitInstance {
    const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = ""

    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClientInstance())
            .build()
    }

    fun getCachedRetrofitInstance(context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getCachedOkHttpClientInstance(context, TimeInterval(1, TimeUnit.DAYS)))
            .build()
    }

    private fun getOkHttpClientInstance(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .addInterceptor(ApiKeyInterceptor(API_KEY))
            .addInterceptor(LoggingInterceptor())
            .build()
    }

    private fun getCachedOkHttpClientInstance(context: Context, duration: TimeInterval): OkHttpClient {
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        val cacheSize: Long = 10 * 1024 * 1024
        val cache = Cache(httpCacheDirectory, cacheSize)
        return getOkHttpClientInstance().newBuilder()
            .addInterceptor(CachedResponseInterceptor(duration = duration))
            .cache(cache)
            .build()
    }
}