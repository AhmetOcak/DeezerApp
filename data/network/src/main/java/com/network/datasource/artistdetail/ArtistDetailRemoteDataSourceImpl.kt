package com.network.datasource.artistdetail

import com.network.model.ArtistDetailDto
import com.network.retrofit.ArtistDetailApi
import javax.inject.Inject

class ArtistDetailRemoteDataSourceImpl @Inject constructor(
    private val api: ArtistDetailApi
) : IArtistDetailRemoteDataSource {

    override suspend fun getArtistDetails(artistId: Long): ArtistDetailDto =
        api.getArtistDetails(artistId)
}