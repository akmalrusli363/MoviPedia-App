package com.tilikki.movipedia.view.main

import android.content.SharedPreferences
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.reactive.asFlow

abstract class BaseMovieViewModel(private val sharedPreferences: AppSharedPreferences) :
    BaseDisposableViewModel() {
    protected val movieRepository: MovieRxRepository = MovieRxRepositoryImpl()

    private val _language = MutableStateFlow(sharedPreferences.getTmdbLanguage())
    private val _region = MutableStateFlow(sharedPreferences.getTmdbRegion())

    private val preferenceChangeListener =
        SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
            when (key) {
                AppSharedPreferences.TMDB_LANGUAGE -> _language.value =
                    sharedPreferences.getTmdbLanguage()

                AppSharedPreferences.TMDB_REGION -> _region.value =
                    sharedPreferences.getTmdbRegion()
            }
        }

    init {
        sharedPreferences.registerSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterSharedPreferenceChangeListener(preferenceChangeListener)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val movieList: Flow<PagingData<Movie>> = combine(_language, _region) { language, region ->
        fetchMovieList(language, region)
    }.flatMapLatest { it }.cachedIn(viewModelScope)

    abstract fun fetchMovieListAsFlowable(
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String? = Constants.DEFAULT_REQUEST_REGION
    ): Flowable<PagingData<Movie>>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMovieList(language: String, region: String?): Flow<PagingData<Movie>> {
        return fetchMovieListAsFlowable(language, region)
            .cachedIn(viewModelScope).asFlow()
    }
}