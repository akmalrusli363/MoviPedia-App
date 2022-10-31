package com.tilikki.movipedia.network

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


class LoggingInterceptor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url
        Log.d("HttpLogger", "Proceeding URL Request $url...")
        return chain.proceed(chain.request())
    }
}