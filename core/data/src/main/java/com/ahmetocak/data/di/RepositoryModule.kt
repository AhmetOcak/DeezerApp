package com.ahmetocak.data.di

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.data.repository.DeezerRepositoryImpl
import com.ahmetocak.database.datasource.FavoriteSongsLocalDataSource
import com.ahmetocak.network.datasource.RemoteDeezerDataSource
import com.ahmetocak.network.retrofit.DeezerApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDeezerRepository(
        remoteDeezerDataSource: RemoteDeezerDataSource,
        localDataSource: FavoriteSongsLocalDataSource,
        deezerApi: DeezerApi
    ): DeezerRepository {
        return DeezerRepositoryImpl(remoteDeezerDataSource, localDataSource, deezerApi)
    }
}