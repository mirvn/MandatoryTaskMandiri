package com.practice.mandatorytaskmandiri.ui.moviesbygenre

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.practice.mandatorytaskmandiri.data.repositories.MoviesByGenreRepo
import com.practice.mandatorytaskmandiri.data.source.remote.response.MoviesByGenreResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

class MoviesByGenreFragmentViewModel(
    private val moviesByGenreRepo: MoviesByGenreRepo
) : ViewModel() {

    suspend fun getMoviesByGenre(
        api_key: String,
        language: String,
        sort_by: String,
        include_adult: String,
        include_video: String,
        page: String,
        with_genres: String,
        with_watch_monetization_types: String
    ): LiveData<ResponseUtil<MoviesByGenreResponse>> = moviesByGenreRepo.getMoviesByGenre(
        api_key = api_key,
        language = language,
        sort_by =  sort_by,
        include_adult = include_adult,
        include_video = include_video,
        page =  page,
        with_genres = with_genres,
        with_watch_monetization_types = with_watch_monetization_types
    )
}
