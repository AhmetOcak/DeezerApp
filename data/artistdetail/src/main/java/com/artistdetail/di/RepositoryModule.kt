package com.artistdetail.di

import com.artistdetail.repository.ArtistDetailRepositoryImpl
import com.artistdetail.repository.IArtistDetailRepository
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
    abstract fun bindArtistDetailRepository(artistDetailRepositoryImpl: ArtistDetailRepositoryImpl): IArtistDetailRepository
}