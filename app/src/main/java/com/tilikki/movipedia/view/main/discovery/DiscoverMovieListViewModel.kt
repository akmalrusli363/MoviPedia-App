package com.tilikki.movipedia.view.main.discovery

import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Single

class DiscoverMovieListViewModel : BaseMovieViewModel() {
    override fun fetchMovieList(
        page: Int,
        language: String,
        region: String
    ): Single<ListResponse<MovieDto>> {
        return movieRepository.getMovieList()
    }
}