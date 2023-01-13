package com.practice.mandatorytaskmandiri.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.practice.mandatorytaskmandiri.data.model.DetailMovieModel

data class DetailMovieResponse(
    @SerializedName("adult")
    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: BelongsToCollection?,
    @SerializedName("budget")
    val budget: Int?,
    @SerializedName("genres")
    val genres: List<Genre?>?,
    @SerializedName("homepage")
    val homepage: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("imdb_id")
    val imdbId: String?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("popularity")
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("production_companies")
    val productionCompanies: List<ProductionCompany?>?,
    @SerializedName("production_countries")
    val productionCountries: List<ProductionCountry?>?,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("revenue")
    val revenue: Int?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage?>?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("tagline")
    val tagline: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("video")
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
) {
    data class BelongsToCollection(
        @SerializedName("backdrop_path")
        val backdropPath: String?,
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("poster_path")
        val posterPath: String?
    )

    data class Genre(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?
    )

    data class ProductionCompany(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("logo_path")
        val logoPath: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("origin_country")
        val originCountry: String?
    )

    data class ProductionCountry(
        @SerializedName("iso_3166_1")
        val iso31661: String?,
        @SerializedName("name")
        val name: String?
    )

    data class SpokenLanguage(
        @SerializedName("english_name")
        val englishName: String?,
        @SerializedName("iso_639_1")
        val iso6391: String?,
        @SerializedName("name")
        val name: String?
    )
}
fun DetailMovieResponse.getBelongsToCollectio(): DetailMovieModel.BelongsToCollection {
    val belongsToCollection = DetailMovieModel.BelongsToCollection(
        backdropPath = this.belongsToCollection?.backdropPath,
        id = this.belongsToCollection?.id,
        name = this.belongsToCollection?.name,
        posterPath = this.belongsToCollection?.posterPath
    )
    return belongsToCollection
}

fun DetailMovieResponse.getGenre(): List<DetailMovieModel.Genre?>? {
    var genre: DetailMovieModel.Genre
    val listofGenre = mutableListOf<DetailMovieModel.Genre>()
    this.genres?.map {
        genre = DetailMovieModel.Genre(
            id = it?.id,
            name = it?.name
        )
        listofGenre.add(genre)
    }
    return listofGenre
}

fun DetailMovieResponse.getProductionCompany(): List<DetailMovieModel.ProductionCompany?>? {
    var productionCompany: DetailMovieModel.ProductionCompany
    val listProductionCompany = mutableListOf<DetailMovieModel.ProductionCompany>()
    this.productionCompanies?.map {
        productionCompany = DetailMovieModel.ProductionCompany(
            id = it?.id,
            logoPath = it?.logoPath,
            name = it?.name,
            originCountry = it?.originCountry
        )
        listProductionCompany.add(productionCompany)
    }
    return listProductionCompany
}

fun DetailMovieResponse.getProductionCountry(): List<DetailMovieModel.ProductionCountry?>? {
    var productionCountry: DetailMovieModel.ProductionCountry
    val listProductionCountries = mutableListOf<DetailMovieModel.ProductionCountry>()
    this.productionCountries?.map {
        productionCountry = DetailMovieModel.ProductionCountry(
            iso31661 = it?.iso31661,
            name = it?.name
        )
        listProductionCountries.add(productionCountry)
    }
    return listProductionCountries
}

fun DetailMovieResponse.getSpokenLanguage(): List<DetailMovieModel.SpokenLanguage?>? {
    var spokenLanguage: DetailMovieModel.SpokenLanguage
    val listSpokenLanguage = mutableListOf<DetailMovieModel.SpokenLanguage>()
    this.spokenLanguages?.map {
        spokenLanguage = DetailMovieModel.SpokenLanguage(
            englishName = it?.englishName,
            iso6391 = it?.iso6391,
            name = it?.name
        )
        listSpokenLanguage.add(spokenLanguage)
    }
    return listSpokenLanguage
}

fun DetailMovieResponse.toDetailMovieModel(): DetailMovieModel {
    val detailMovieModel = DetailMovieModel(
        adult = adult,
        backdropPath = backdropPath,
        belongsToCollection = this.getBelongsToCollectio(),
        budget = budget,
        genres = this.getGenre(),
        homepage = homepage,
        id = id,
        imdbId = imdbId,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        productionCompanies = this.getProductionCompany(),
        productionCountries = this.getProductionCountry(),
        releaseDate = releaseDate,
        revenue = revenue,
        runtime = runtime,
        spokenLanguages = this.getSpokenLanguage(),
        status = status,
        tagline = tagline,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
    return detailMovieModel
}
