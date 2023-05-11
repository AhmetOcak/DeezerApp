package com.artistdetail.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.artistdetail.toArtistDetail
import com.artistdetail.toTrackData
import com.model.ArtistDetail
import com.model.TrackData
import com.network.datasource.artistdetail.IArtistDetailRemoteDataSource
import com.network.datasource.artistdetail.TrackListPagingDataSource
import com.network.retrofit.ArtistDetailApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistDetailRepositoryImpl @Inject constructor(
    private val dataSource: IArtistDetailRemoteDataSource,
    private var artistDetailApi: ArtistDetailApi
) : IArtistDetailRepository{

    override suspend fun getArtistDetails(artistId: Int): ArtistDetail {
        return dataSource.getArtistDetails(artistId).toArtistDetail()
    }

    override fun getTrackList(artistId: Int): Flow<PagingData<TrackData>> {
        val trackListPagingDataSource = TrackListPagingDataSource(
            api = artistDetailApi,
            artistId = artistId
        )

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            pagingSourceFactory = { trackListPagingDataSource }
        ).flow.map {
            it.map { trackDataDto ->
                trackDataDto.toTrackData()
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }
}