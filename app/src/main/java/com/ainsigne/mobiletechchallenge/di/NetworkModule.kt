package com.ainsigne.mobiletechchallenge.di

import com.ainsigne.mobiletechchallenge.api.OmdbService
import com.ainsigne.mobiletechchallenge.utils.BASE_API
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Network module for containing web service instances and api helpers
 */
val networkModule = module {
    single { createWebService() }
}

/**
 * Creates a web service implementation
 * @return as [OmdbService] web service initialized
 */
fun createWebService() : OmdbService {
    val interceptor = HttpLoggingInterceptor()
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).addInterceptor(interceptor).readTimeout(15, TimeUnit.SECONDS).build()


    return Retrofit.Builder()
        .baseUrl(BASE_API).client(client)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build().create(OmdbService::class.java)
}
