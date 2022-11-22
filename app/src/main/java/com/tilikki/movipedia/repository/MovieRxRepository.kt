package com.tilikki.movipedia.repository

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.model.general.TimeRangeType
import io.reactivex.Flowable

interface MovieRxRepository {
    fun getMovieList(
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Flowable<PagingData<Movie>>

    fun getUpcomingMovieList(
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Flowable<PagingData<Movie>>

    fun getTopRatedMovieList(
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Flowable<PagingData<Movie>>

    fun getTrendingMovieList(
        timeRangeType: TimeRangeType,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null
    ): Flowable<PagingData<Movie>>

    fun searchForMovie(
        searchQuery: String,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null,
    ): Flowable<PagingData<Movie>>

    fun getMovieListByGenreId(
        genreId: Int,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = null,
    ): Flowable<PagingData<Movie>>

    val pagingConfig: PagingConfig
        get() = PagingConfig(
            pageSize = Constants.PAGE_SIZE,
            enablePlaceholders = true,
        )
}