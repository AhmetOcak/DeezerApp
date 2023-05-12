package com.network.datasource.albumdetails

import com.network.model.albumdetail.AlbumDetailsDto
import com.network.retrofit.AlbumDetailsApi
import javax.inject.Inject

class AlbumDetailsRemoteDataSourceImpl @Inject constructor(
    private val api: AlbumDetailsApi
) : IAlbumDetailsRemoteDataSource {

    override suspend fun getAlbumDetails(albumId: Int): AlbumDetailsDto =
        api.getAlbumDetails(albumId)

}