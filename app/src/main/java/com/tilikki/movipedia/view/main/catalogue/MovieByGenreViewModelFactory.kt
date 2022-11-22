package com.tilikki.movipedia.view.main.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tilikki.movipedia.db.MovieDatabase
import com.tilikki.movipedia.repository.AppSharedPreferences

class MovieByGenreViewModelFactory(
    private val genreId: Int,
    private val database: MovieDatabase,
    private val sharedPreferences: AppSharedPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieByGenreViewModel(genreId, database, sharedPreferences) as T
    }
}