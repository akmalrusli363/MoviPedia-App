package com.tilikki.movipedia.view.main.trending

import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.model.general.TimeRangeType
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Single

class TrendingMovieViewModel : BaseMovieViewModel() {
    override fun fetchMovieList(
        page: Int,
        language: String,
        region: String
    ): Single<ListResponse<MovieDto>> {
        return movieRepository.getTrendingMovieList(
            timeRangeType = TimeRangeType.WEEK
        )
    }

    override fun onCleared() {
        super.onCleared()
    }
}