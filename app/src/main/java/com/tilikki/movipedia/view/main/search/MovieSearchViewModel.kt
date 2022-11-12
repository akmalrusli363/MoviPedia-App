package com.tilikki.movipedia.view.main.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.repository.MovieRxRepository
import com.tilikki.movipedia.repository.MovieRxRepositoryImpl
import com.tilikki.movipedia.util.swapList
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow

class MovieSearchViewModel : BaseDisposableViewModel() {

    var isSearching by mutableStateOf(false)
    var searchQuery = MutableStateFlow("")
    val genreList: MutableList<Genre> = mutableStateListOf()

    @OptIn(ExperimentalCoroutinesApi::class)
    var movieList: Flow<PagingData<Movie>> = searchQuery.flatMapLatest { query ->
        fetchMovieList(query)
    }

    private val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl()
    }
    private val movieRxRepository: MovieRxRepository by lazy {
        MovieRxRepositoryImpl()
    }

    fun debouncedSearchOnType(searchQuery: String, delay: Long = 1000L) {
        viewModelScope.launch {
            delay(delay)
            searchMovieList(searchQuery)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMovieList(searchQuery: String): Flow<PagingData<Movie>> {
        return movieRxRepository.searchForMovie(searchQuery)
            .cachedIn(viewModelScope).asFlow()
    }

    fun searchMovieList(searchQuery: String) {
        this.searchQuery.value = searchQuery
        this.isSearching = true
    }

    fun getGenreList() {
        val disposableFetchGenreList = movieRepository.getGenreList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val domainGenreList = it.toDomainGenreList()
                genreList.swapList(domainGenreList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
        compositeDisposable.addAll(disposableFetchGenreList)
    }
}