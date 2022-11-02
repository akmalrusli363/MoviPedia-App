package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.MovieDetail
import io.reactivex.Single

interface MovieRepository {
    fun getMovieList(
        page: Int = 1,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Single<ListResponse<MovieDto>>

    fun getUpcomingMovieList(
        page: Int = 1,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Single<ListResponse<MovieDto>>

    fun getTopRatedMovieList(
        page: Int = 1,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Single<ListResponse<MovieDto>>

    fun searchForMovie(searchQuery: String): Single<ListResponse<MovieDto>>
    fun getMovieDetail(id: Int): Single<MovieDetail>
}