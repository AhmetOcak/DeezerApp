package com.network.datasource.albumdetails

import com.network.model.albumdetail.AlbumDetailsDto

interface IAlbumDetailsRemoteDataSource {

    suspend fun getAlbumDetails(albumId: Int): AlbumDetailsDto
}