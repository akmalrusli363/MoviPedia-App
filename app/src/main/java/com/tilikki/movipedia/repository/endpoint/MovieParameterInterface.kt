package com.tilikki.movipedia.repository.endpoint

import com.tilikki.movipedia.dto.GenreListDto
import com.tilikki.movipedia.dto.KeywordDto
import com.tilikki.movipedia.dto.MovieParameterDto
import com.tilikki.movipedia.dto.VideoDto
import com.tilikki.movipedia.helper.Constants
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieParameterInterface {
    @GET("genre/movie/list")
    fun getGenreList(
        @Query("language") language: String = Constants.DEFAULT_REQUEST_LANGUAGE,
    ): Single<GenreListDto>

    @GET("movie/{movie_id}/keywords")
    fun getMovieKeywords(
        @Path("movie_id") movieId: Int,
    ): Single<MovieParameterDto<KeywordDto>>

    @GET("movie/{movie_id}/videos")
    fun getMovieVideos(
        @Path("movie_id") movieId: Int,
    ): Single<MovieParameterDto<VideoDto>>
}