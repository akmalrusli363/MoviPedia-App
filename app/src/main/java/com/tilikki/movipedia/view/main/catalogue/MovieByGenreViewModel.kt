package com.tilikki.movipedia.view.main.catalogue

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.paging.PagingData
import com.tilikki.movipedia.db.MovieDatabase
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.repository.AppSharedPreferences
import com.tilikki.movipedia.repository.MoviePropertiesRepository
import com.tilikki.movipedia.repository.MoviePropertiesRepositoryImpl
import com.tilikki.movipedia.view.main.BaseMovieViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow

class MovieByGenreViewModel(
    genreId: Int,
    movieDatabase: MovieDatabase,
    sharedPreferences: AppSharedPreferences
) : BaseMovieViewModel(sharedPreferences) {
    val genreId: MutableState<Int> by lazy { mutableStateOf(genreId) }
    val genreName = MutableStateFlow("")

    private val moviePropertiesRepository: MoviePropertiesRepository by lazy {
        MoviePropertiesRepositoryImpl(movieDatabase)
    }

    fun fetchGenreNameFromDb() {
        val disposableFetchGenreById = moviePropertiesRepository.getGenreById(genreId.value)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ genre ->
                genreName.value = genre.name
            }, { err ->
                Log.e("MvFetcher", err.message, err)
            })
        compositeDisposable.addAll(disposableFetchGenreById)
    }

    override fun fetchMovieListAsFlowable(
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return movieRepository.getMovieListByGenreId(genreId = genreId.value, language, region)
    }
}