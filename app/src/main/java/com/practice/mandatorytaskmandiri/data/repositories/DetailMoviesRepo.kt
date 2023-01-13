package com.practice.mandatorytaskmandiri.data.repositories

import androidx.lifecycle.LiveData
import com.practice.mandatorytaskmandiri.data.source.remote.response.DetailMovieResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MovieReviewResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MovieTrailerResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

interface DetailMoviesRepo {
    suspend fun getDetailMovie(
        api_key: String,
        language: String,
        movieId: Int
    ): LiveData<ResponseUtil<DetailMovieResponse>>

    suspend fun getReviewMovie(
        api_key: String,
        language: String,
        page: Int,
        movieId: Int
    ): LiveData<ResponseUtil<MovieReviewResponse>>

    suspend fun getMovieTrailer(
        api_key: String,
        movieId: Int
    ): LiveData<ResponseUtil<MovieTrailerResponse>>
}
