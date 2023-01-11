package com.practice.mandatorytaskmandiri.data.repositories

import androidx.lifecycle.LiveData
import com.practice.mandatorytaskmandiri.data.source.remote.response.GenreResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

interface GenreRepo {
    suspend fun getGenres(): LiveData<ResponseUtil<GenreResponse>>
}
