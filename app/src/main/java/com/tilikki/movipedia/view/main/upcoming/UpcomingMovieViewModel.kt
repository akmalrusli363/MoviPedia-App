package com.tilikki.movipedia.view.main.upcoming

import androidx.paging.PagingData
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable

class UpcomingMovieViewModel(
    sharedPreferences: AppSharedPreferences
) : BaseMovieViewModel(sharedPreferences) {
    override fun fetchMovieListAsFlowable(
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getUpcomingMovieList(language, region)
    }
}