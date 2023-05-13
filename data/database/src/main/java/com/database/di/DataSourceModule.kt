package com.database.di

import com.database.datasource.FavoriteSongsLocalDataSourceImpl
import com.database.datasource.IFavoriteSongsLocalDataSource
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
    abstract fun bindFavoriteSongsLocalDataSource(favoriteSongsLocalDataSourceImpl: FavoriteSongsLocalDataSourceImpl): IFavoriteSongsLocalDataSource
}