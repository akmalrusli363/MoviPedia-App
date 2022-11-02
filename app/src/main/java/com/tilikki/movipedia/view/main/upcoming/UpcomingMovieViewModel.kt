package com.tilikki.movipedia.view.main.upcoming

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.util.swapList
import io.reactivex.schedulers.Schedulers

class UpcomingMovieViewModel : ViewModel() {
    val upcomingMovieList: MutableList<Movie> = mutableStateListOf()
    private val movieRepository: MovieRepository by lazy(::MovieRepositoryImpl)

    fun getUpcomingMovieList() {
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        movieRepository.getUpcomingMovieList()
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val movieList = it.result.map { res -> res.toDomainMovie() }
                upcomingMovieList.swapList(movieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
    }
}