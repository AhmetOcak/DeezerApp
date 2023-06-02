package com.artistdetail.repository

import com.models.ArtistAlbums
import com.models.ArtistDetail

interface IArtistDetailRepository {

    suspend fun getArtistDetails(artistId: Long): ArtistDetail

    suspend fun getArtistAlbums(artistId: Long): ArtistAlbums
}