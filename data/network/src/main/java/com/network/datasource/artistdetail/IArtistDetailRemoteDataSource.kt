package com.network.datasource.artistdetail

import com.network.model.ArtistDetailDto

interface IArtistDetailRemoteDataSource {

    suspend fun getArtistDetails(artistId: Int): ArtistDetailDto
}