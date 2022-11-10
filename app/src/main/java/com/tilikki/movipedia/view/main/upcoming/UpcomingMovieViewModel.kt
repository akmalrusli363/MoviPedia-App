package com.tilikki.movipedia.view.main.upcoming

import androidx.paging.PagingData
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable

class UpcomingMovieViewModel : BaseMovieViewModel() {
    override fun fetchMovieListAsFlowable(
        page: Int,
        language: String,
        region: String
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getUpcomingMovieList()
    }
}