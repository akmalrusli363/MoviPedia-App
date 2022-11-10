package com.tilikki.movipedia.view.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.model.general.FetchState
import com.tilikki.movipedia.repository.MovieRxRepository
import com.tilikki.movipedia.repository.MovieRxRepositoryImpl
import com.tilikki.movipedia.view.BaseDisposableViewModel
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow

abstract class BaseMovieViewModel : BaseDisposableViewModel() {
    protected val movieRepository: MovieRxRepository = MovieRxRepositoryImpl()

    //    val movieList: MutableList<Movie> = mutableStateListOf()
    val movieList: Flow<PagingData<Movie>> = fetchMovieList()
    var fetchState by mutableStateOf(FetchState.defaultState())

    abstract fun fetchMovieListAsFlowable(
        page: Int = 1,
        language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        region: String = "ID"
    ): Flowable<PagingData<Movie>>

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchMovieList(): Flow<PagingData<Movie>> {
        return fetchMovieListAsFlowable()
            .cachedIn(viewModelScope).asFlow()
    }

//    fun getMovieList(onSuccess: (List<Movie>) -> Unit = {}, onError: (Exception) -> Unit = {}) {
//        fetchState = FetchState.loading()
//        val disposableFetchMovieList = getMovieList()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                Log.d("MvFetcher", it.toString())
//                val domainMovieList = it.map { res -> res.toDomainMovie() }
//                fetchState = FetchState.defaultState()
//                movieList.swapList(domainMovieList)
//                onSuccess(domainMovieList)
//            }, { err ->
//                Log.e("MvFetcher", err.message, err)
//                fetchState = FetchState.failedState(err as Exception)
//                onError(err)
//            })
//        compositeDisposable.addAll(disposableFetchMovieList)
//    }

    companion object {
        val defaultFetchState = Pair(false, null)
    }
}