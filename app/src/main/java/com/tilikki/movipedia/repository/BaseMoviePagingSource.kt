package com.tilikki.movipedia.repository

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.Movie
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

class BaseMoviePagingSource(
    private val dataSourceList: (page: Int) -> Single<ListResponse<MovieDto>>
) : RxPagingSource<Int, Movie>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Movie>> {
        val position = params.key ?: Constants.START_PAGE
        return dataSourceList(position).subscribeOn(Schedulers.io())
            .map { listResponse ->
                toLoadResult(listResponse, position)
            }
            .onErrorReturn { ex ->
                return@onErrorReturn LoadResult.Error(ex)
            }
    }

    private fun toLoadResult(
        response: ListResponse<MovieDto>,
        position: Int
    ): LoadResult<Int, Movie> {
        return LoadResult.Page(
            data = response.result.map(MovieDto::toDomainMovie),
            prevKey = when (position) {
                Constants.START_PAGE -> null
                else -> position - 1
            },
            nextKey = when {
                position >= response.totalPages -> null
                else -> position + 1
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}