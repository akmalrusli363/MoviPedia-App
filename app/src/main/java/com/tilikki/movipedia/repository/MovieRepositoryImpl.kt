package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.network.RetrofitInstance
import com.tilikki.movipedia.repository.endpoint.MovieInterface
import io.reactivex.Single

class MovieRepositoryImpl : MovieRepository {
    private val retrofit = RetrofitInstance.getRetrofitInstance()
    private val movieInterface = retrofit.create(MovieInterface::class.java)

    override fun getMovieList(): Single<ListResponse<MovieDto>> {
        return movieInterface.getDiscoverMovieList(page = 1)
    }

    override fun getUpcomingMovieList(): Single<ListResponse<MovieDto>> {
        return movieInterface.getUpcomingMovieList(page = 1)
    }

    override fun getTopRatedMovieList(): Single<ListResponse<MovieDto>> {
        return movieInterface.getTopRatedMovieList(page = 1)
    }

    override fun searchForMovie(searchQuery: String): Single<ListResponse<MovieDto>> {
        TODO("Not yet implemented")
    }

    override fun getMovieDetail(id: Int): Single<MovieDetail> {
        return movieInterface.getMovieDetail(id).map {
            it.toDomainMovieDetail()
        }
    }
}