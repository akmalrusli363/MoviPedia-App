package com.tilikki.movipedia.view.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel : ViewModel() {
    val movieDetail: LiveData<MovieDetail>
        get() = _movieDetail

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private var _movieDetail: MutableLiveData<MovieDetail> = MutableLiveData()
    private var _movieId: MutableLiveData<Int> = MutableLiveData()
    private var _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    fun getMovieDetail(movieId: Int) {
        _movieId.postValue(movieId)
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        _isLoading.postValue(true)
        movieRepository.getMovieDetail(movieId)
            .subscribeOn(Schedulers.io())
            .subscribe({ movie ->
                Log.d("MvFetcher", movie.toString())
                _movieDetail.postValue(movie)
                _isLoading.postValue(false)
            }, { err ->
                Log.e("MvFetcher", err.message, err)
                _isLoading.postValue(false)
            })
    }
}