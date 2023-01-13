package com.practice.mandatorytaskmandiri.ui.detailmovie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.practice.mandatorytaskmandiri.data.repositories.DetailMoviesRepo
import com.practice.mandatorytaskmandiri.data.source.remote.response.DetailMovieResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MovieReviewResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MovieTrailerResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

class DetailMoviesViewModel(
    private val detailMoviesRepo: DetailMoviesRepo
) : ViewModel() {
    suspend fun getDetailMovie(
        apikey: String,
        language: String,
        movieId: Int
    ): LiveData<ResponseUtil<DetailMovieResponse>> = detailMoviesRepo.getDetailMovie(
        api_key = apikey,
        language = language,
        movieId = movieId
    )

    suspend fun getReviewMovie(
        apikey: String,
        language: String,
        page: Int,
        movieId: Int
    ): LiveData<ResponseUtil<MovieReviewResponse>> = detailMoviesRepo.getReviewMovie(
        api_key = apikey,
        language = language,
        page = page,
        movieId = movieId
    )

    suspend fun getMovieTrailer(
        apikey: String,
        movieId: Int
    ): LiveData<ResponseUtil<MovieTrailerResponse>> = detailMoviesRepo.getMovieTrailer(
        api_key = apikey,
        movieId = movieId
    )
}
