package com.tilikki.movipedia.view.main.catalogue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MovieByGenreViewModelFactory(private val genreId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MovieByGenreViewModel(genreId) as T
    }
}