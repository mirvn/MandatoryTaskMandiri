package com.practice.mandatorytaskmandiri.data.source.remote.response

import com.google.gson.annotations.SerializedName
import com.practice.mandatorytaskmandiri.data.model.GenreModel

data class GenreResponse(
    @SerializedName("genres")
    val genres: List<Genre?>?
) {
    data class Genre(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("name")
        val name: String?
    )
}

fun GenreResponse.toListGenre(): MutableList<GenreModel> {
    val listData: MutableList<GenreModel> = mutableListOf()

    this.genres?.map { genre ->
        val genresItem: GenreModel = GenreModel(
            genre?.id,
            genre?.name
        )
        listData.add(genresItem)
    }
    return listData
}
