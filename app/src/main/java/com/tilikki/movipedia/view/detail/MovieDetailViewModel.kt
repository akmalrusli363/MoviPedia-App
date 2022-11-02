package com.tilikki.movipedia.view.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel : ViewModel() {
    var movieDetail: MovieDetail? by mutableStateOf(null)
    var isLoading: Boolean by mutableStateOf(false)

    private var _movieId: MutableLiveData<Int> = MutableLiveData()

    fun getMovieDetail(movieId: Int): Disposable {
        _movieId.postValue(movieId)
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        isLoading = true
        return movieRepository.getMovieDetail(movieId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movie ->
                Log.d("MvFetcher", movie.toString())
                movieDetail = movie
                isLoading = false
            }, { err ->
                Log.e("MvFetcher", err.message, err)
                isLoading = false
            })
    }
}