package com.tilikki.movipedia.view.main.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tilikki.movipedia.db.MovieDatabase

@Suppress("UNCHECKED_CAST")
class MovieSearchViewModelFactory(private val movieDatabase: MovieDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieSearchViewModel(movieDatabase) as T
    }
}