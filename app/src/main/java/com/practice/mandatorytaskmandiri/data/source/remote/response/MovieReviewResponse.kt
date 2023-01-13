package com.practice.mandatorytaskmandiri.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class MovieReviewResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("results")
    val results: List<Result?>?,
    @SerializedName("total_pages")
    val totalPages: Int?,
    @SerializedName("total_results")
    val totalResults: Int?
) {
    data class Result(
        @SerializedName("author")
        val author: String?,
        @SerializedName("author_details")
        val authorDetails: AuthorDetails?,
        @SerializedName("content")
        val content: String?,
        @SerializedName("created_at")
        val createdAt: String?,
        @SerializedName("id")
        val id: String?,
        @SerializedName("updated_at")
        val updatedAt: String?,
        @SerializedName("url")
        val url: String?
    ) {
        data class AuthorDetails(
            @SerializedName("avatar_path")
            val avatarPath: String?,
            @SerializedName("name")
            val name: String?,
            @SerializedName("rating")
            val rating: Double?,
            @SerializedName("username")
            val username: String?
        )
    }
}

// fun MovieReviewResponse.toMovieReviewModel(): MovieReviewModel {
//    var reviewResult: MovieReviewModel.Result
//    this.results?.map {
//        val authorDetailsMap = it?.authorDetails
//        val authorDetailValue = MovieReviewModel.Result.AuthorDetails(
//            avatarPath = authorDetailsMap?.avatarPath,
//            name = authorDetailsMap?.name,
//            rating = authorDetailsMap?.rating,
//            username = authorDetailsMap?.username
//        )
//        reviewResult = MovieReviewModel.Result(
//            author = it?.author,
//            authorDetails = it?.authorDetails,
//            content = it?.content,
//            createdAt = it?.createdAt,
//            id = it?.id,
//            updatedAt = it?.updatedAt,
//            url = it?.url
//        )
//    }
//
// }
