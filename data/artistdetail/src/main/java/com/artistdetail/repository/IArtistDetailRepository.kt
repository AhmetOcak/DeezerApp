package com.artistdetail.repository

import com.model.ArtistAlbums
import com.model.ArtistDetail

interface IArtistDetailRepository {

    suspend fun getArtistDetails(artistId: Long): ArtistDetail

    suspend fun getArtistAlbums(artistId: Long): ArtistAlbums
}