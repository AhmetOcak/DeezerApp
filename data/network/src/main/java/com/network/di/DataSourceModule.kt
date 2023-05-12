package com.network.di

import com.network.datasource.albumdetails.AlbumDetailsRemoteDataSourceImpl
import com.network.datasource.albumdetails.IAlbumDetailsRemoteDataSource
import com.network.datasource.artist.*
import com.network.datasource.artistdetail.ArtistDetailRemoteDataSourceImpl
import com.network.datasource.artistdetail.IArtistDetailRemoteDataSource
import com.network.datasource.musicgenre.*
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
    abstract fun bindMusicGenreRemoteDataSource(musicGenreRemoteDataSourceImpl: MusicGenreRemoteDataSourceImpl): IMusicGenreRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindArtistRemoteDataSource(artistRemoteDataSourceImpl: ArtistRemoteDataSourceImpl): IArtistRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindArtistDetailRemoteDataSource(artistDetailRemoteDataSourceImpl: ArtistDetailRemoteDataSourceImpl): IArtistDetailRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAlbumDetailsRemoteDataSource(albumDetailRemoteDataSourceImpl: AlbumDetailsRemoteDataSourceImpl): IAlbumDetailsRemoteDataSource
}