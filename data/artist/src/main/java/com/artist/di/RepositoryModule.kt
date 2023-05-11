package com.artist.di

import com.artist.repository.ArtistRepositoryImpl
import com.artist.repository.IArtistRepository
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
    abstract fun bindArtistRepository(artistRepositoryImpl: ArtistRepositoryImpl): IArtistRepository
}