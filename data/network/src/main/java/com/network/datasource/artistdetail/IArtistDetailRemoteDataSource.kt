package com.network.datasource.artistdetail

import com.network.model.ArtistAlbumsDto
import com.network.model.ArtistDetailDto

interface IArtistDetailRemoteDataSource {

    suspend fun getArtistDetails(artistId: Long): ArtistDetailDto

    suspend fun getArtistAlbums(artistId: Long): ArtistAlbumsDto
}