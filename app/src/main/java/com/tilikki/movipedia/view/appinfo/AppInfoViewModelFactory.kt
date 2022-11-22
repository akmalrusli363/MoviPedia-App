package com.tilikki.movipedia.view.appinfo

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tilikki.movipedia.repository.AppConfigRepository
import com.tilikki.movipedia.repository.AppConfigRepositoryImpl
import com.tilikki.movipedia.repository.AppSharedPreferences

@Suppress("UNCHECKED_CAST")
class AppInfoViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val appConfigRepo: AppConfigRepository by lazy {
            AppConfigRepositoryImpl(context)
        }
        val sharedPreferences = AppSharedPreferences(context)
        return AppInfoViewModel(appConfigRepo, sharedPreferences) as T
    }
}