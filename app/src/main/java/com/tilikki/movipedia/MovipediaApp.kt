package com.tilikki.movipedia

import android.app.Application
import android.content.Context

class MovipediaApp: Application() {
    companion object {
        var appContext: Context? = null
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}