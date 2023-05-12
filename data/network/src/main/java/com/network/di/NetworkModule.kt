package com.network.di

import com.network.BaseUrl
import com.network.retrofit.AlbumDetailsApi
import com.network.retrofit.ArtistApi
import com.network.retrofit.ArtistDetailApi
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
           .baseUrl(BaseUrl.DEEZER)
           .addConverterFactory(GsonConverterFactory.create())
           .build()
           .create(MusicGenreApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArtistApi(): ArtistApi {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.DEEZER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtistApi::class.java)
    }

    @Provides
    @Singleton
    fun provideArtistDetailApi(): ArtistDetailApi {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.DEEZER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ArtistDetailApi::class.java)
    }

    @Provides
    @Singleton
    fun provideAlbumDetailsApi(): AlbumDetailsApi {
        return Retrofit.Builder()
            .baseUrl(BaseUrl.DEEZER)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AlbumDetailsApi::class.java)
    }
}