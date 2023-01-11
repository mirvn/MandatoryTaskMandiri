package com.practice.mandatorytaskmandiri.ui.genres

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.practice.mandatorytaskmandiri.data.repositories.GenreRepo
import com.practice.mandatorytaskmandiri.data.source.remote.response.GenreResponse
import com.practice.mandatorytaskmandiri.utils.ResponseUtil

class GenreListFragmentViewModel(
    private val genreRepo: GenreRepo
) : ViewModel() {

    suspend fun getGenre(): LiveData<ResponseUtil<GenreResponse>> = genreRepo.getGenres()
}
