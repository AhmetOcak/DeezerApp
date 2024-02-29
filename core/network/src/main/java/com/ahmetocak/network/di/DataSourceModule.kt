package com.ahmetocak.network.di

import com.ahmetocak.network.datasource.RemoteDeezerDataSource
import com.ahmetocak.network.datasource.RemoteDeezerDataSourceImpl
import com.ahmetocak.network.retrofit.DeezerApi
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
    fun provideDeezerDataSource(api: DeezerApi): RemoteDeezerDataSource {
        return RemoteDeezerDataSourceImpl(api = api)
    }
}