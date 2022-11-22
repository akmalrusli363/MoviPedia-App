package com.tilikki.movipedia.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import com.tilikki.movipedia.model.general.TimeRangeType
import com.tilikki.movipedia.network.RetrofitInstance
import com.tilikki.movipedia.repository.endpoint.MovieInterface
import io.reactivex.Flowable
import io.reactivex.Single

class MovieRxRepositoryImpl : MovieRxRepository {
    private val retrofit = RetrofitInstance.getRetrofitInstance()
    private val movieInterface = retrofit.create(MovieInterface::class.java)

    override fun getMovieList(
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return buildFlowablePagingData { page ->
            movieInterface.getDiscoverMovieList(page, language, region)
        }
    }

    override fun getUpcomingMovieList(
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return buildFlowablePagingData { page ->
            movieInterface.getUpcomingMovieList(page, language, region)
        }
    }

    override fun getTopRatedMovieList(
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return buildFlowablePagingData { page ->
            movieInterface.getTopRatedMovieList(page, language, region)
        }
    }

    override fun getTrendingMovieList(
        timeRangeType: TimeRangeType,
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return buildFlowablePagingData { page ->
            movieInterface.getTrendingMovieList(
                timeRangeType = timeRangeType,
                page = page,
                language = language,
                region = region
            )
        }
    }

    override fun searchForMovie(
        searchQuery: String,
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return buildFlowablePagingData { page ->
            movieInterface.searchForMovies(
                page = page,
                query = searchQuery,
                language = language,
                region = region
            )
        }
    }

    override fun getMovieListByGenreId(
        genreId: Int,
        language: String,
        region: String?
    ): Flowable<PagingData<Movie>> {
        return buildFlowablePagingData { page ->
            movieInterface.getDiscoverMovieList(
                page = page,
                language = language,
                region = region,
                genreIds = "$genreId"
            )
        }
    }

    private fun buildFlowablePagingData(
        dataSourceList: (page: Int) -> Single<ListResponse<MovieDto>>
    ): Flowable<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.PAGE_SIZE,
                enablePlaceholders = true,
                prefetchDistance = 5,
            ),
            pagingSourceFactory = {
                BaseMoviePagingSource(dataSourceList)
            }
        ).flowable
    }
}