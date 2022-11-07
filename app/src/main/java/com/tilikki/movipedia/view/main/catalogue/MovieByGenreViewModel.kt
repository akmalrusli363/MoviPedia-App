package com.tilikki.movipedia.view.main.catalogue

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Single

class MovieByGenreViewModel(genreId: Int) : BaseMovieViewModel() {
    val genreId: MutableState<Int> by lazy { mutableStateOf(genreId) }

    override fun fetchMovieList(
        page: Int,
        language: String,
        region: String
    ): Single<ListResponse<MovieDto>> {
        return movieRepository.getMovieListByGenreId(genreId = genreId.value)
    }
}