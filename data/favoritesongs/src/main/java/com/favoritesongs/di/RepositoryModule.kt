package com.favoritesongs.di

import com.favoritesongs.repository.FavoriteSongRepositoryImpl
import com.favoritesongs.repository.IFavoriteSongRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindFavoriteSongRepository(favoriteSongRepositoryImpl: FavoriteSongRepositoryImpl): IFavoriteSongRepository
}