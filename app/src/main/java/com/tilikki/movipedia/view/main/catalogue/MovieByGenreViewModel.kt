package com.tilikki.movipedia.view.main.catalogue

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable

class MovieByGenreViewModel(genreId: Int) : BaseMovieViewModel() {
    val genreId: MutableState<Int> by lazy { mutableStateOf(genreId) }

    override fun fetchMovieListAsFlowable(
        page: Int,
        language: String,
        region: String
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getMovieListByGenreId(genreId = genreId.value)
    }
}