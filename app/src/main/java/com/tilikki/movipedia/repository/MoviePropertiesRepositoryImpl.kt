package com.tilikki.movipedia.repository

import android.util.Log
import com.tilikki.movipedia.db.MovieDatabase
import com.tilikki.movipedia.model.Genre
import com.tilikki.movipedia.network.RetrofitInstance
import com.tilikki.movipedia.repository.endpoint.MovieParameterInterface
import io.reactivex.Observable
import io.reactivex.Single

class MoviePropertiesRepositoryImpl(
    private val database: MovieDatabase
) : MoviePropertiesRepository {

    private val retrofit = RetrofitInstance.getRetrofitInstance()
    private val movieParameterInterface = retrofit.create(MovieParameterInterface::class.java)

    override fun getGenreList(): Observable<List<Genre>> {
        val fallbackObservable = database.genreDao().getAll()
            .doOnNext {
                Log.d("mvfetcher", "fetched locally $it")
            }.doOnError {
                Log.e("mvfetcher", "on nyo, error occurred! $it", it)
            }
        val genreList = movieParameterInterface.getGenreList()
            .map { dto ->
                val genreList = dto.toDomainGenreList()
                Log.d("mvfetcher", "fetched & insert to db $genreList")
                database.genreDao().insertAll(genreList)
                genreList
            }
            .toObservable()
            .onErrorResumeNext(fallbackObservable)

        return genreList
    }

    override fun getGenreById(genreId: Int): Single<Genre> {
        return database.genreDao().getGenreById(genreId)
    }
}