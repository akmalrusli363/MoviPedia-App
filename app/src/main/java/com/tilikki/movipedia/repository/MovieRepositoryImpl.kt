package com.tilikki.movipedia.repository

import com.tilikki.movipedia.dto.GenreListDto
import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.model.MovieDetail
import com.tilikki.movipedia.model.general.TimeRangeType
import com.tilikki.movipedia.network.RetrofitInstance
import com.tilikki.movipedia.repository.endpoint.MovieInterface
import com.tilikki.movipedia.repository.endpoint.MovieParameterInterface
import io.reactivex.Single

class MovieRepositoryImpl : MovieRepository {
    private val retrofit = RetrofitInstance.getRetrofitInstance()
    private val movieInterface = retrofit.create(MovieInterface::class.java)
    private val movieParameterInterface = retrofit.create(MovieParameterInterface::class.java)

    override fun getMovieList(
        page: Int,
        language: String,
        region: String?
    ): Single<ListResponse<MovieDto>> {
        return movieInterface.getDiscoverMovieList(page, language, region)
    }

    override fun getUpcomingMovieList(
        page: Int,
        language: String,
        region: String?
    ): Single<ListResponse<MovieDto>> {
        return movieInterface.getUpcomingMovieList(page, language, region)
    }

    override fun getTopRatedMovieList(
        page: Int,
        language: String,
        region: String?
    ): Single<ListResponse<MovieDto>> {
        return movieInterface.getTopRatedMovieList(page, language, region)
    }

    override fun getTrendingMovieList(
        page: Int,
        timeRangeType: TimeRangeType,
        language: String,
        region: String?
    ): Single<ListResponse<MovieDto>> {
        return movieInterface.getTrendingMovieList(timeRangeType, page, language, region)
    }

    override fun searchForMovie(searchQuery: String): Single<ListResponse<MovieDto>> {
        return movieInterface.searchForMovies(searchQuery)
    }

    override fun getMovieDetail(id: Int): Single<MovieDetail> {
        return movieInterface.getMovieDetail(id).map {
            it.toDomainMovieDetail()
        }
    }

    override fun getGenreList(): Single<GenreListDto> {
        return movieParameterInterface.getGenreList()
    }
}