package com.tilikki.movipedia.view.main

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.model.general.FetchState
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.util.swapList
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseMovieViewModel : BaseDisposableViewModel() {
    val movieList: MutableList<Movie> = mutableStateListOf()
    var fetchState by mutableStateOf(FetchState.defaultState())

    protected val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl()
    }

    abstract fun fetchMovieList(
        page: Int = 1,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String = "ID"
    ): Single<ListResponse<MovieDto>>

    fun getMovieList(onSuccess: (List<Movie>) -> Unit = {}, onError: (Exception) -> Unit = {}) {
        fetchState = FetchState.loading()
        val disposableFetchMovieList = fetchMovieList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val domainMovieList = it.result.map { res -> res.toDomainMovie() }
                fetchState = FetchState.defaultState()
                movieList.swapList(domainMovieList)
                onSuccess(domainMovieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
                fetchState = FetchState.failedState(err as Exception)
                onError(err)
            })
        compositeDisposable.addAll(disposableFetchMovieList)
    }

    companion object {
        val defaultFetchState = Pair(false, null)
    }
}