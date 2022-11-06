package com.tilikki.movipedia.view.main.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.util.swapList
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MovieSearchViewModel : BaseDisposableViewModel() {

    var searchQuery by mutableStateOf("")
    var isSearching by mutableStateOf(false)
    val movieList: MutableList<Movie> = mutableStateListOf()
    val genreList: MutableList<Genre> = mutableStateListOf()

    private val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl()
    }

    fun clearSearch() {
        this.searchQuery = ""
    }

    fun debouncedSearchOnType(searchQuery: String, delay: Long = 300L) {
        viewModelScope.launch {
            delay(delay)
            searchMovieList(searchQuery)
        }
    }

    fun searchMovieList(searchQuery: String) {
        this.searchQuery = searchQuery
        this.isSearching = true
        val disposableFetchMovieList = movieRepository.searchForMovie(searchQuery)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val domainMovieList = it.result.map { res -> res.toDomainMovie() }
                movieList.swapList(domainMovieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
        compositeDisposable.addAll(disposableFetchMovieList)
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