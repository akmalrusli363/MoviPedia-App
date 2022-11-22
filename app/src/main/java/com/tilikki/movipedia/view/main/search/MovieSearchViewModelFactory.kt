package com.tilikki.movipedia.view.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tilikki.movipedia.db.MovieDatabase
import com.tilikki.movipedia.repository.AppSharedPreferences

@Suppress("UNCHECKED_CAST")
class MovieSearchViewModelFactory(
    private val movieDatabase: MovieDatabase,
    private val sharedPreferences: AppSharedPreferences
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(movieDatabase, sharedPreferences) as T
    }
}