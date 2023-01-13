package com.practice.mandatorytaskmandiri.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiServices
import com.practice.mandatorytaskmandiri.data.source.remote.response.DetailMovieResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MovieReviewResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MovieTrailerResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

private const val TAG = "GenreRepoImplementation"

class DetailMovieRepoImplementation(private val apiServices: ApiServices) : DetailMoviesRepo {
    companion object {
        @Volatile
        private var instance: DetailMovieRepoImplementation? = null

        fun getInstance(
            apiServices: ApiServices
        ): DetailMovieRepoImplementation {
            return instance ?: synchronized(this) {
                instance ?: DetailMovieRepoImplementation(apiServices)
            }
        }
    }

    override suspend fun getDetailMovie(
        api_key: String,
        language: String,
        movieId: Int
    ): LiveData<ResponseUtil<DetailMovieResponse>> {
        val resultDetailMovie = MutableLiveData<ResponseUtil<DetailMovieResponse>>().apply {
            value = ResponseUtil.Loading
        }
        return try {
            val response = apiServices.getDetailMovie(
                apiKey = api_key,
                language = language,
                movie_id = movieId
            )
            val result = response.body()
            if (response.isSuccessful && result != null) {
                resultDetailMovie.value = ResponseUtil.Success(result)
                resultDetailMovie
            } else {
//                val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
                resultDetailMovie.value = ResponseUtil.Error(response.message())
                resultDetailMovie
            }
        } catch (e: java.lang.Exception) {
            resultDetailMovie.value =
                ResponseUtil.Error(e.message.toString() ?: "An Error Occurred")
            resultDetailMovie
        }
    }

    override suspend fun getReviewMovie(
        api_key: String,
        language: String,
        page: Int,
        movieId: Int
    ): LiveData<ResponseUtil<MovieReviewResponse>> {
        val resultReviewMovie = MutableLiveData<ResponseUtil<MovieReviewResponse>>().apply {
            value = ResponseUtil.Loading
        }
        return try {
            val response = apiServices.getReviewMovie(
                apiKey = api_key,
                language = language,
                movie_id = movieId,
                page = page
            )
            val result = response.body()
            if (response.isSuccessful && result != null) {
                resultReviewMovie.value = ResponseUtil.Success(result)
                resultReviewMovie
            } else {
//                val jsonObj = JSONObject(response.errorBody()!!.charStream().readText())
//                Log.e(TAG, "getReviewMovie-jsonError: $jsonObj")
                resultReviewMovie.value = ResponseUtil.Error(response.message())
                resultReviewMovie
            }
        } catch (e: java.lang.Exception) {
            resultReviewMovie.value =
                ResponseUtil.Error(e.message.toString() ?: "An Error Occurred")
            resultReviewMovie
        }
    }

    override suspend fun getMovieTrailer(
        api_key: String,
        movieId: Int
    ): LiveData<ResponseUtil<MovieTrailerResponse>> {
        val resultMovieTrailer = MutableLiveData<ResponseUtil<MovieTrailerResponse>>().apply {
            value = ResponseUtil.Loading
        }
        return try {
            val response = apiServices.getMovieTrailer(
                apiKey = api_key,
                movie_id = movieId
            )
            val result = response.body()
            if (response.isSuccessful && result != null) {
                resultMovieTrailer.value = ResponseUtil.Success(result)
                resultMovieTrailer
            } else {
                resultMovieTrailer.value = ResponseUtil.Error(response.message())
                resultMovieTrailer
            }
        } catch (e: java.lang.Exception) {
            resultMovieTrailer.value =
                ResponseUtil.Error(e.message.toString() ?: "An Error Occurred")
            resultMovieTrailer
        }
    }
}
