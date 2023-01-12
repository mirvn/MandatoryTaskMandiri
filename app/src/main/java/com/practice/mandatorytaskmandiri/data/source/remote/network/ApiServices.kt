package com.practice.mandatorytaskmandiri.data.source.remote.network

import com.practice.mandatorytaskmandiri.data.source.remote.response.GenreResponse
import com.practice.mandatorytaskmandiri.data.source.remote.response.MoviesByGenreResponse
import retrofit2.Response
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface ApiServices {
//    @POST("/login")
//    suspend fun login(@Body requestAuth: AuthRequest): Response<AuthModel>

//    @POST("/transfer")
//    suspend fun transfer(@Header("Authorization") auth: String,@Body requestTransferRequest: TransferRequest) :  Response<TransferModel>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Response<GenreResponse>

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("sort_by") sort_by: String,
        @Query("include_adult") include_adult: String,
        @Query("include_video") include_video: String,
        @Query("page") page: String,
        @Query("with_genres") with_genres: String,
        @Query("with_watch_monetization_types") with_watch_monetization_types: String,
    ): Response<MoviesByGenreResponse>
}
