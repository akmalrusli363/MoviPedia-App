package com.tilikki.movipedia.view.main.trending

import androidx.paging.PagingData
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.model.general.TimeRangeType
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable

class TrendingMovieViewModel : BaseMovieViewModel() {
    override fun fetchMovieListAsFlowable(
        page: Int,
        language: String,
        region: String
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getTrendingMovieList(
            timeRangeType = TimeRangeType.WEEK
        )
    }
}