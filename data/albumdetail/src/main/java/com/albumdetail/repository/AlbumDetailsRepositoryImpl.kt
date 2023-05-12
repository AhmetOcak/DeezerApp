package com.albumdetail.repository

import com.albumdetail.toAlbumDetails
import com.model.albumdetail.AlbumDetails
import com.network.datasource.albumdetails.IAlbumDetailsRemoteDataSource
import javax.inject.Inject

class AlbumDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: IAlbumDetailsRemoteDataSource
) : IAlbumDetailRepository {

    override suspend fun getAlbumDetails(albumId: Int): AlbumDetails {
        return remoteDataSource.getAlbumDetails(albumId).toAlbumDetails()
    }
}