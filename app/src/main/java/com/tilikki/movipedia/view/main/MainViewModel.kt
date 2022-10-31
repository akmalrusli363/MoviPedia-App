package com.tilikki.movipedia.view.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    val movieList: LiveData<List<Movie>>
        get() = _movieList
    val upcomingMovieList: LiveData<List<Movie>>
        get() = _upcomingMovieList

    private var _movieList: MutableLiveData<List<Movie>> = MutableLiveData()
    private var _upcomingMovieList: MutableLiveData<List<Movie>> = MutableLiveData()

    fun getMovieList() {
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        movieRepository.getMovieList()
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val movieList = it.result.map { res -> res.toDomainMovie() }
               _movieList.postValue(movieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
    }

    fun getUpcomingMovieList() {
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        movieRepository.getUpcomingMovieList()
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val movieList = it.result.map { res -> res.toDomainMovie() }
                _movieList.postValue(movieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
    }
}