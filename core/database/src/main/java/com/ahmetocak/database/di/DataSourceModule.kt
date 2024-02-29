package com.ahmetocak.database.di

import com.ahmetocak.database.dao.FavoriteSongsDao
import com.ahmetocak.database.datasource.FavoriteSongsLocalDataSourceImpl
import com.ahmetocak.database.datasource.FavoriteSongsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Singleton
    @Provides
    fun provideFavoriteSongsLocalDataSource(
        favoriteDao: FavoriteSongsDao
    ): FavoriteSongsLocalDataSource {
        return FavoriteSongsLocalDataSourceImpl(dao = favoriteDao)
    }
}