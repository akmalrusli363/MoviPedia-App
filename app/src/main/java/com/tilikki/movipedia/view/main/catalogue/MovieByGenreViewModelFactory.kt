package com.tilikki.movipedia.view.main.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tilikki.movipedia.db.MovieDatabase

class MovieByGenreViewModelFactory(private val genreId: Int, private val database: MovieDatabase) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieByGenreViewModel(genreId, database) as T
    }
}