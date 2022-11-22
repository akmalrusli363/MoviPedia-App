package com.tilikki.movipedia.view.detail

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.repository.MovieRepository
import com.tilikki.movipedia.repository.MovieRepositoryImpl
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailViewModel(val sharedPreferences: AppSharedPreferences) :
    BaseDisposableViewModel() {
    var movieDetail: MovieDetail? by mutableStateOf(null)
    var isLoading: Boolean by mutableStateOf(false)

    private var _movieId: MutableLiveData<Int> = MutableLiveData()

    fun getMovieDetail(movieId: Int) {
        _movieId.postValue(movieId)
        isLoading = true
        val movieRepository: MovieRepository = MovieRepositoryImpl()
        val language = sharedPreferences.getTmdbLanguage()
        val disposableFetchMovieDetail = movieRepository
            .getMovieDetailWithAdditionalFields(movieId, language)
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
        compositeDisposable.addAll(disposableFetchMovieDetail)
    }
}