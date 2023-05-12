package com.albumdetail.di

import com.albumdetail.repository.AlbumDetailsRepositoryImpl
import com.albumdetail.repository.IAlbumDetailRepository
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
    abstract fun bindAlbumDetailsRepository(albumDetailsRepositoryImpl: AlbumDetailsRepositoryImpl): IAlbumDetailRepository
}