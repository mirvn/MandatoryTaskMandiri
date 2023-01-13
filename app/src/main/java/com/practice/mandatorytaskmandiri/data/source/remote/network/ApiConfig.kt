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
    private const val NETWORK_CALL_TIMEOUT = 10L

    private fun provideHttpInterceptor(context: Context): Interceptor {
        return Interceptor { chain ->
            var request = chain.request()
            request = request.newBuilder()
                .build()
            return@Interceptor chain.proceed(request)
        }
    }

    // logging with chucker
//    private fun provideLoggingChuckerInterceptor(context: Context): ChuckerInterceptor {
//        return ChuckerInterceptor.Builder(context).build()
//    }

    private fun provideHttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(provideHttpInterceptor(context))
//            addInterceptor(provideLoggingChuckerInterceptor(context)) // for Logging Purposes
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
