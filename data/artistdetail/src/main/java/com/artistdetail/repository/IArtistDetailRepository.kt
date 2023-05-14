package com.artistdetail.repository

import com.model.ArtistAlbums
import com.model.ArtistDetail

interface IArtistDetailRepository {

    suspend fun getArtistDetails(artistId: Int): ArtistDetail

    suspend fun getArtistAlbums(artistId: Int): ArtistAlbums
}