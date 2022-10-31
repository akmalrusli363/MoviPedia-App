package com.tilikki.movipedia.network

import com.tilikki.movipedia.util.TimeInterval
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class CachedResponseInterceptor(
    private val maxAge: Int, private val timeUnit: TimeUnit
) : Interceptor {

    constructor(duration: TimeInterval) : this(duration.interval, duration.timeUnit)

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(maxAge, timeUnit)
            .build()
        return response.newBuilder()
            .removeHeader("Pragma")
            .removeHeader("Cache-Control")
            .header("Cache-Control", cacheControl.toString())
            .build()
    }
}