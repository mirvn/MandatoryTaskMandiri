package com.practice.mandatorytaskmandiri.data.source.remote.response

import MovieTrailerModel
import com.google.gson.annotations.SerializedName

data class MovieTrailerResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("results")
    val results: List<Result?>?
) {
    data class Result(
        @SerializedName("id")
        val id: String?,
        @SerializedName("iso_3166_1")
        val iso31661: String?,
        @SerializedName("iso_639_1")
        val iso6391: String?,
        @SerializedName("key")
        val key: String?,
        @SerializedName("name")
        val name: String?,
        @SerializedName("official")
        val official: Boolean?,
        @SerializedName("published_at")
        val publishedAt: String?,
        @SerializedName("site")
        val site: String?,
        @SerializedName("size")
        val size: Int?,
        @SerializedName("type")
        val type: String?
    )
}

fun MovieTrailerResponse.toMovieTrailerModel(): MovieTrailerModel {
    val id = this.id
    val resultList = mutableListOf<MovieTrailerModel.Result>()
    this.results?.map {
        val result = MovieTrailerModel.Result(
            id = it?.id,
            iso31661 = it?.iso31661,
            iso6391 = it?.iso6391,
            key = it?.key,
            name = it?.name,
            official = it?.official,
            publishedAt = it?.publishedAt,
            site = it?.site,
            size = it?.size,
            type = it?.type
        )
        resultList.add(result)
    }
    return MovieTrailerModel(id, resultList)
}
