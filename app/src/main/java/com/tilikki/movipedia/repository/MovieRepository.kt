package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.model.MovieDetail
import io.reactivex.Single

interface MovieRepository {
    fun getMovieList(): Single<ListResponse<MovieDto>>
    fun getUpcomingMovieList(): Single<ListResponse<MovieDto>>
    fun getTopRatedMovieList(): Single<ListResponse<MovieDto>>
    fun searchForMovie(searchQuery: String): Single<ListResponse<MovieDto>>
    fun getMovieDetail(id: Int): Single<MovieDetail>
}