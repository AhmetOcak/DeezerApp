package com.network.di

import com.network.BaseUrl
import com.network.retrofit.MusicGenreApi
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
    fun provideMusicGenreApi(): MusicGenreApi {
       return Retrofit.Builder()
           .baseUrl(BaseUrl.MUSIC_GENRE_BASEURL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(MusicGenreApi::class.java)
    }
}