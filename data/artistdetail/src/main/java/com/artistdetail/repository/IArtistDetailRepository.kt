package com.artistdetail.repository

import androidx.paging.PagingData
import com.models.ArtistAlbums
import com.models.ArtistDetail
import kotlinx.coroutines.flow.Flow

interface IArtistDetailRepository {

    suspend fun getArtistDetails(artistId: Long): ArtistDetail

    fun getArtistAlbums(artistId: Long): Flow<PagingData<ArtistAlbums>>
}