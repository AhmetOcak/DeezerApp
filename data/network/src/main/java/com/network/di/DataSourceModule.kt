package com.network.di

import com.network.datasource.musicgenre.IMusicGenreRemoteDataSource
import com.network.datasource.musicgenre.MusicGenreRemoteDataSourceImpl
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
    abstract fun bindMusicGenreDataSource(musicGenreRemoteDataSourceImpl: MusicGenreRemoteDataSourceImpl): IMusicGenreRemoteDataSource
}