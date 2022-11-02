package com.tilikki.movipedia.view.main

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.util.swapList
import io.reactivex.schedulers.Schedulers

class DiscoverMovieListViewModel : ViewModel() {
    val discoverMovieList: MutableList<Movie> = mutableStateListOf<Movie>()
    private val movieRepository: MovieRepository by lazy(::MovieRepositoryImpl)

    fun getMovieList() {
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        movieRepository.getMovieList()
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val movieList = it.result.map { res -> res.toDomainMovie() }
                discoverMovieList.swapList(movieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
    }
}