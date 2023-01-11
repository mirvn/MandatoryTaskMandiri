package com.practice.mandatorytaskmandiri.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.practice.mandatorytaskmandiri.BuildConfig
import com.practice.mandatorytaskmandiri.data.source.remote.network.ApiServices
import com.practice.mandatorytaskmandiri.data.source.remote.response.GenreResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

private const val TAG = "GenreRepoImplementation"
class GenreRepoImplementation(private val apiServices: ApiServices) : GenreRepo {
    companion object {
        @Volatile
        private var instance: GenreRepoImplementation? = null

        fun getInstance(
            apiServices: ApiServices
        ): GenreRepoImplementation {
            return instance ?: synchronized(this) {
                instance ?: GenreRepoImplementation(apiServices)
            }
        }
    }
    override suspend fun getGenres(): LiveData<ResponseUtil<GenreResponse>> {
        val resultGenre = MutableLiveData<ResponseUtil<GenreResponse>>().apply {
            value = ResponseUtil.Loading
        }
        return try {
            val response = apiServices.getGenres(BuildConfig.API_KEY_TOKEN, "en-US")
            val result = response.body()
            if (response.isSuccessful && result != null) {
                resultGenre.value = ResponseUtil.Success(result)
                resultGenre
            } else {
                resultGenre.value = ResponseUtil.Error(response.message())
                resultGenre
            }
        } catch (e: java.lang.Exception) {
            resultGenre.value = ResponseUtil.Error(e.message.toString() ?: "An Error Occurred")
            resultGenre
        }
    }
}
