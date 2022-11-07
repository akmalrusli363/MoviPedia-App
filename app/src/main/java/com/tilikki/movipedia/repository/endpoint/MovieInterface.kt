package com.tilikki.movipedia.repository.endpoint

import com.tilikki.movipedia.dto.ListResponse
import com.tilikki.movipedia.dto.MovieDetailDto
import com.tilikki.movipedia.dto.MovieDto
import com.tilikki.movipedia.helper.Constants
import com.tilikki.movipedia.model.general.TimeRangeType
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieInterface {
    @GET("movie/popular")
    fun getPopularMovieList(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        @Query("region") region: String? = null,
    ): Single<ListResponse<MovieDto>>

    @GET("discover/movie")
    fun getDiscoverMovieList(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        @Query("region") region: String? = null,
        @Query("with_genres") genreIds: String? = null,
    ): Single<ListResponse<MovieDto>>

    @GET("movie/top_rated")
    fun getTopRatedMovieList(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        @Query("region") region: String? = null,
    ): Single<ListResponse<MovieDto>>

    @GET("movie/upcoming")
    fun getUpcomingMovieList(
        @Query("page") page: Int = 1,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        @Query("region") region: String? = null,
    ): Single<ListResponse<MovieDto>>

    @GET("trending/movie/{time_window}")
    fun getTrendingMovieList(
        @Path("time_window") timeRangeType: TimeRangeType,
        @Query("page") page: Int = 1,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
        @Query("region") region: String? = null,
    ): Single<ListResponse<MovieDto>>

    @GET("search/movie")
    fun searchForMovies(
        @Query("query") query: String,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE
    ): Single<ListResponse<MovieDto>>

    @GET("movie/latest")
    fun getLatestMovie(
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE
    ): Single<MovieDetailDto>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieId: Int,
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
    ): Single<MovieDetailDto>
}