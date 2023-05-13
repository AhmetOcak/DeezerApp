package com.database.di

import com.database.dao.FavoriteSongsDao
import com.database.db.FavoriteSongsDatabase
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