package com.practice.mandatorytaskmandiri.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.practice.mandatorytaskmandiri.data.repositories.DetailMovieRepoImplementation
import com.practice.mandatorytaskmandiri.data.repositories.GenreRepoImplementation
import com.practice.mandatorytaskmandiri.data.repositories.MoviesByGenreRepoImplementation
import com.practice.mandatorytaskmandiri.ui.detailmovie.DetailMoviesViewModel
import com.practice.mandatorytaskmandiri.ui.genres.GenreListFragmentViewModel
import com.practice.mandatorytaskmandiri.ui.moviesbygenre.MoviesByGenreFragmentViewModel

class ViewModelFactory {
    inner class ViewModelFactoryGenreList(
        private val genreRepoImplementation: GenreRepoImplementation
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return GenreListFragmentViewModel(genreRepoImplementation) as T
        }
    }

    inner class ViewModelFactoryMoviesByGenre(
        private val moviesByGenreRepoImplementation: MoviesByGenreRepoImplementation
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MoviesByGenreFragmentViewModel(moviesByGenreRepoImplementation) as T
        }
    }

    inner class ViewModelFactoryDetailMovies(
        private val detailMovieRepoImplementation: DetailMovieRepoImplementation
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return DetailMoviesViewModel(detailMovieRepoImplementation) as T
        }
    }
}
