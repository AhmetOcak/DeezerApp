package com.albumdetail.repository

import com.albumdetail.mapper.toAlbumDetails
import com.albumdetail.mapper.toFavoriteSongsEntity
import com.albumdetail.mapper.toFavoriteSongsList
import com.database.datasource.IFavoriteSongsLocalDataSource
import com.model.FavoriteSongs
import com.model.albumdetail.AlbumDetails
import com.network.datasource.albumdetails.IAlbumDetailsRemoteDataSource
import javax.inject.Inject

class AlbumDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: IAlbumDetailsRemoteDataSource,
    private val localDataSource: IFavoriteSongsLocalDataSource
) : IAlbumDetailRepository {

    override suspend fun getAlbumDetails(albumId: Long): AlbumDetails {
        return remoteDataSource.getAlbumDetails(albumId).toAlbumDetails()
    }

    override suspend fun getAllFavoriteSongs(): List<FavoriteSongs> {
        return localDataSource.getAllFavoriteSongs().toFavoriteSongsList()
    }

    override suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs) {
        return localDataSource.addFavoriteSong(favoriteSongs.toFavoriteSongsEntity())
    }

    override suspend fun removeFavoriteSong(songId: Long) {
        return localDataSource.removeFavoriteSong(songId)
    }
}