package com.network.di

import com.network.datasource.artist.*
import com.network.datasource.musicgenre.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindMusicGenreRemoteDataSource(musicGenreRemoteDataSourceImpl: MusicGenreRemoteDataSourceImpl): IMusicGenreRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindArtistRemoteDataSource(artistRemoteDataSourceImpl: ArtistRemoteDataSourceImpl): IArtistRemoteDataSource
}