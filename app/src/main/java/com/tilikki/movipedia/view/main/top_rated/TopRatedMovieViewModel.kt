package com.tilikki.movipedia.view.main.top_rated

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.util.swapList
import io.reactivex.schedulers.Schedulers

class TopRatedMovieViewModel : ViewModel() {
    val topRatedMovieList: MutableList<Movie> = mutableStateListOf()
    private val movieRepository: MovieRepository by lazy(::MovieRepositoryImpl)

    fun getTopRatedMovieList() {
        movieRepository.getTopRatedMovieList()
            .subscribeOn(Schedulers.io())
            .subscribe({
                Log.d("MvFetcher", it.toString())
                val movieList = it.result.map { res -> res.toDomainMovie() }
                topRatedMovieList.swapList(movieList)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
    }
}