package com.tilikki.movipedia.view.main.discovery

import androidx.paging.PagingData
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable

class DiscoverMovieListViewModel : BaseMovieViewModel() {
    override fun fetchMovieListAsFlowable(
        page: Int,
        language: String,
        region: String
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getMovieList()
    }
}