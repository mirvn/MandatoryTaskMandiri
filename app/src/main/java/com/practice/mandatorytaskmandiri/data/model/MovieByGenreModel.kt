package com.practice.mandatorytaskmandiri.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieByGenreModel(
    val page: Int?,
    val results: MutableList<Result>,
    val totalPages: Int?,
    val totalResults: Int?
) : Parcelable {
    @Parcelize
    data class Result(
        val adult: Boolean?,
        val backdropPath: String?,
        val genreIds: List<Int?>?,
        val id: Int?,
        val originalLanguage: String?,
        val originalTitle: String?,
        val overview: String?,
        val popularity: Double?,
        val posterPath: String?,
        val releaseDate: String?,
        val title: String?,
        val video: Boolean?,
        val voteAverage: Double?,
        val voteCount: Int?
    ) : Parcelable
}
