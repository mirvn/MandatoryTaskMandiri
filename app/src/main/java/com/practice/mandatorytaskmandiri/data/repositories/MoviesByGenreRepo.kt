package com.practice.mandatorytaskmandiri.data.repositories

import androidx.lifecycle.LiveData
import com.practice.mandatorytaskmandiri.data.source.remote.response.MoviesByGenreResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

interface MoviesByGenreRepo {
    suspend fun getMoviesByGenre(
        api_key: String,
        language: String,
        sort_by: String,
        include_adult: String,
        include_video: String,
        page: String,
        with_genres: String,
        with_watch_monetization_types: String
    ): LiveData<ResponseUtil<MoviesByGenreResponse>>
}