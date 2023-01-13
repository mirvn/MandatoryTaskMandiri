package com.practice.mandatorytaskmandiri.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DetailMovieModel(
    val adult: Boolean?,
    val backdropPath: String?,
    val belongsToCollection: BelongsToCollection?,
    val budget: Int?,
    val genres: List<Genre?>?,
    val homepage: String?,
    val id: Int?,
    val imdbId: String?,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterPath: String?,
    val productionCompanies: List<ProductionCompany?>?,
    val productionCountries: List<ProductionCountry?>?,
    val releaseDate: String?,
    val revenue: Int?,
    val runtime: Int?,
    val spokenLanguages: List<SpokenLanguage?>?,
    val status: String?,
    val tagline: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?
) : Parcelable {
    @Parcelize
    data class BelongsToCollection(
        val backdropPath: String?,
        val id: Int?,
        val name: String?,
        val posterPath: String?
    ) : Parcelable

    @Parcelize
    data class Genre(
        var id: Int?,
        val name: String?
    ) : Parcelable

    @Parcelize
    data class ProductionCompany(
        val id: Int?,
        val logoPath: String?,
        val name: String?,
        val originCountry: String?
    ) : Parcelable

    @Parcelize
    data class ProductionCountry(
        val iso31661: String?,
        val name: String?
    ) : Parcelable

    @Parcelize
    data class SpokenLanguage(
        val englishName: String?,
        val iso6391: String?,
        val name: String?
    ) : Parcelable
}
