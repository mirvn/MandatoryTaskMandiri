package com.practice.mandatorytaskmandiri.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.mandatorytaskmandiri.data.repositories.GenreRepo
import com.practice.mandatorytaskmandiri.data.repositories.GenreRepoImplementation
import com.practice.mandatorytaskmandiri.ui.genres.GenreListFragmentViewModel

class ViewModelFactory(private val argument: GenreRepoImplementation) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GenreListFragmentViewModel(argument) as T
    }
}
