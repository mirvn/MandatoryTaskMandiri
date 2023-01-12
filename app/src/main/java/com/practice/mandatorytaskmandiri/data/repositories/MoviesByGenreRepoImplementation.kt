package com.practice.mandatorytaskmandiri.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiServices
import com.practice.mandatorytaskmandiri.data.source.remote.response.MoviesByGenreResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

private const val TAG = "GenreRepoImplementation"
class MoviesByGenreRepoImplementation(private val apiServices: ApiServices) : MoviesByGenreRepo {
    companion object {
        @Volatile
        private var instance: MoviesByGenreRepoImplementation? = null

        fun getInstance(
            apiServices: ApiServices
        ): MoviesByGenreRepoImplementation {
            return instance ?: synchronized(this) {
                instance ?: MoviesByGenreRepoImplementation(apiServices)
            }
        }
    }
    override suspend fun getMoviesByGenre(
        api_key: String,
        language: String,
        sort_by: String,
        include_adult: String,
        include_video: String,
        page: String,
        with_genres: String,
        with_watch_monetization_types: String
    ): LiveData<ResponseUtil<MoviesByGenreResponse>> {
        val resultMoviesByGenre = MutableLiveData<ResponseUtil<MoviesByGenreResponse>>().apply {
            value = ResponseUtil.Loading
        }
        return try {
            val response = apiServices.getMoviesByGenre(
                apiKey = api_key,
                language = language,
                sort_by = sort_by,
                include_adult = include_adult,
                include_video = include_video,
                page = page,
                with_genres = with_genres,
                with_watch_monetization_types = with_watch_monetization_types
            )
            val result = response.body()
            if (response.isSuccessful && result != null) {
                resultMoviesByGenre.value = ResponseUtil.Success(result)
                resultMoviesByGenre
            } else {
                resultMoviesByGenre.value = ResponseUtil.Error(response.message())
                resultMoviesByGenre
            }
        } catch (e: java.lang.Exception) {
            resultMoviesByGenre.value = ResponseUtil.Error(e.message.toString() ?: "An Error Occurred")
            resultMoviesByGenre
        }
    }
}
