package com.artistdetail.repository

import com.artistdetail.mapper.toArtistAlbums
import com.artistdetail.toArtistDetail
import com.model.ArtistAlbums
import com.model.ArtistDetail
import com.network.datasource.artistdetail.IArtistDetailRemoteDataSource
import javax.inject.Inject

class ArtistDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: IArtistDetailRemoteDataSource
) : IArtistDetailRepository{

    override suspend fun getArtistDetails(artistId: Int): ArtistDetail {
        return remoteDataSource.getArtistDetails(artistId).toArtistDetail()
    }

    override suspend fun getArtistAlbums(artistId: Int): ArtistAlbums {
        return remoteDataSource.getArtistAlbums(artistId).toArtistAlbums()
    }

}