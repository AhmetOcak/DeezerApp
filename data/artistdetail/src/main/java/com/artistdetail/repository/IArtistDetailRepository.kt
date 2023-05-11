package com.artistdetail.repository

import androidx.paging.PagingData
import com.model.ArtistDetail
import com.model.TrackData
import kotlinx.coroutines.flow.Flow

interface IArtistDetailRepository {

    suspend fun getArtistDetails(artistId: Int): ArtistDetail

    fun getTrackList(artistId: Int): Flow<PagingData<TrackData>>
}