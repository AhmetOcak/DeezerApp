package com.ahmetocak.database.di

import com.ahmetocak.database.datasource.FavoriteSongsLocalDataSourceImpl
import com.ahmetocak.database.datasource.FavoriteSongsLocalDataSource
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
    abstract fun bindFavoriteSongsLocalDataSource(
        favoriteSongsLocalDataSourceImpl: FavoriteSongsLocalDataSourceImpl
    ): FavoriteSongsLocalDataSource
}