package com.ahmetocak.database.di

import com.ahmetocak.database.dao.FavoriteSongsDao
import com.ahmetocak.database.db.FavoriteSongsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideFavoriteSongsDao(favoriteSongsDatabase: FavoriteSongsDatabase): FavoriteSongsDao =
        favoriteSongsDatabase.favoriteSongsDao()
}