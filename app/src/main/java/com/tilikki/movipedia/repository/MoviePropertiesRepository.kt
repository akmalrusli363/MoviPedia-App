package com.tilikki.movipedia.repository

import com.tilikki.movipedia.model.Genre
import io.reactivex.Observable
import io.reactivex.Single

interface MoviePropertiesRepository {
    fun getGenreList(): Observable<List<Genre>>
    fun getGenreById(genreId: Int): Single<Genre>
}