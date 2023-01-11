package com.practice.mandatorytaskmandiri.data.source.remote.network

import com.practice.mandatorytaskmandiri.data.source.remote.response.GenreResponse
import retrofit2.Response
import retrofit2.http.* // ktlint-disable no-wildcard-imports

interface ApiServices {
//    @POST("/login")
//    suspend fun login(@Body requestAuth: AuthRequest): Response<AuthModel>
//
//    @POST("/register")
//    suspend fun register(@Body requestAuth: AuthRequest): Response<AuthModel>
//
//    @POST("/transfer")
//    suspend fun transfer(@Header("Authorization") auth: String,@Body requestTransferRequest: TransferRequest) :  Response<TransferModel>

    @GET("/genre/movie/list")
    suspend fun getGenres(@Query("api_key") apiKey: String): Response<GenreResponse>

//    @GET("/payees")
//    suspend fun payees(@Header("Authorization") auth: String) : Response<PayeesModel>
//
//    @GET("/transactions")
//    suspend fun transactionsList(@Header("Authorization") auth: String): Response<TransactionListModel>
}
