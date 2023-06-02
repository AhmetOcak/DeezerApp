package com.artistdetail.repository

import com.artistdetail.mapper.toArtistAlbums
import com.artistdetail.mapper.toArtistDetail
import com.models.ArtistAlbums
import com.models.ArtistDetail
import com.network.datasource.artistdetail.IArtistDetailRemoteDataSource
import javax.inject.Inject

class ArtistDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: IArtistDetailRemoteDataSource
) : IArtistDetailRepository{

    override suspend fun getArtistDetails(artistId: Long): ArtistDetail {
        return remoteDataSource.getArtistDetails(artistId).toArtistDetail()
    }

    override suspend fun getArtistAlbums(artistId: Long): ArtistAlbums {
        return remoteDataSource.getArtistAlbums(artistId).toArtistAlbums()
    }

}