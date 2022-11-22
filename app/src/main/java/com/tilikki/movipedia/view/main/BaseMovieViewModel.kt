package com.tilikki.movipedia.view.main

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.repository.MovieRxRepository
import com.tilikki.movipedia.repository.MovieRxRepositoryImpl
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow

abstract class BaseMovieViewModel(private val sharedPreferences: AppSharedPreferences) :
    BaseDisposableViewModel() {
    protected val movieRepository: MovieRxRepository = MovieRxRepositoryImpl()

    val movieList: Flow<PagingData<Movie>> by lazy { fetchMovieList() }

    abstract fun fetchMovieListAsFlowable(
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = "ID"
    ): Flowable<PagingData<Movie>>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMovieList(): Flow<PagingData<Movie>> {
        val language = sharedPreferences.getTmdbLanguage()
        val region = sharedPreferences.getTmdbRegion()
        return fetchMovieListAsFlowable(language, region)
            .cachedIn(viewModelScope).asFlow()
    }
}