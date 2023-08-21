package com.tilikki.movipedia.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tilikki.movipedia.repository.AppSharedPreferences

@Suppress("UNCHECKED_CAST")
class ThemeEngineViewModelFactory(
    private val context: Context
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val sharedPreferences = AppSharedPreferences(context)
        return ThemeEngineViewModel(sharedPreferences) as T
    }
}