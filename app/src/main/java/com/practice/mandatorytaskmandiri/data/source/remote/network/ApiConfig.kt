package com.practice.mandatorytaskmandiri.data.source.remote.network

import android.content.Context
import com.google.gson.GsonBuilder
import com.practice.mandatorytaskmandiri.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {

    private const val BASE_URL = BuildConfig.URL_TMDB
    private const val NETWORK_CALL_TIMEOUT = 120L

    private fun provideHttpInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            val token = BuildConfig.API_KEY_TOKEN
            request = request.newBuilder()
//                .addHeader("api_key", token)
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

    private fun provideHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(provideHttpInterceptor(context))
            /*if (BuildConfig.DEBUG) {
                addInterceptor(provideLoggingInterceptor(context))
            }*/
            callTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            connectTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(NETWORK_CALL_TIMEOUT, TimeUnit.SECONDS)
        }.build()
    }

    fun provideApiService(context: Context): ApiServices {
        val gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create()
        return Retrofit.Builder().apply {
            baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(provideHttpClient(context))
        }.build().create(ApiServices::class.java)
    }
}
