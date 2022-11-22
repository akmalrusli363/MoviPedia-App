package com.tilikki.movipedia.view.main.discovery

import androidx.paging.PagingData
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable

class DiscoverMovieListViewModel(
    sharedPreferences: AppSharedPreferences
) : BaseMovieViewModel(sharedPreferences) {
    override fun fetchMovieListAsFlowable(
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getMovieList(language, region)
    }
}