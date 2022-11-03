package com.tilikki.movipedia.view.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.util.swapList
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

abstract class BaseMovieViewModel : BaseDisposableViewModel() {
    val movieList: MutableList<Movie> = mutableStateListOf()

    protected val movieRepository: MovieRepository by lazy {
        MovieRepositoryImpl()
    }

    abstract fun fetchMovieList(
        page: Int = 1,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String = "ID"
    ): Single<ListResponse<MovieDto>>

    fun getMovieList() {
        val disposableFetchMovieList = fetchMovieList()
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
}