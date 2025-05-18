package com.example.s8090565assignment2.di

import com.example.s8090565assignment2.network.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Dagger Hilt module to provide network-related dependencies
@Module
@InstallIn(SingletonComponent::class) // Module lifespan tied to the whole app lifecycle
object NetworkModule {

    private const val BASE_URL = "https://nit3213api.onrender.com/" // API base URL

    // Provides a singleton OkHttpClient instance used for networking
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    // Provides a singleton Retrofit instance configured with base URL and Gson converter
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()) // For JSON serialization/deserialization
            .build()

    // Provides the ApiService interface implementation from Retrofit
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}
