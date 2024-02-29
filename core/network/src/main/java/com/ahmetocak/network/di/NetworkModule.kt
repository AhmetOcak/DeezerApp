package com.ahmetocak.network.di

import com.ahmetocak.network.BaseUrl
import com.ahmetocak.network.retrofit.DeezerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDeezerApi(): DeezerApi {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.DEEZER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DeezerApi::class.java)
    }
}