package com.artistdetail.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.artistdetail.mapper.toArtistAlbums
import com.artistdetail.mapper.toArtistDetail
import com.models.ArtistAlbums
import com.models.ArtistDetail
import com.network.datasource.artistdetail.ArtistAlbumsPagingSource
import com.network.datasource.artistdetail.IArtistDetailRemoteDataSource
import com.network.retrofit.ArtistDetailApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArtistDetailRepositoryImpl @Inject constructor(
    private val remoteDataSource: IArtistDetailRemoteDataSource,
    private val api: ArtistDetailApi
) : IArtistDetailRepository{

    override suspend fun getArtistDetails(artistId: Long): ArtistDetail {
        return remoteDataSource.getArtistDetails(artistId).toArtistDetail()
    }

    override fun getArtistAlbums(artistId: Long): Flow<PagingData<ArtistAlbums>> {
        val artistAlbumsPagingSource = ArtistAlbumsPagingSource(artistId, api)
        return Pager(
            config = PagingConfig(
                pageSize = 25
            ),
            pagingSourceFactory = { artistAlbumsPagingSource }
        ).flow.map { pagingData ->
            pagingData.map { albumsDto ->
                albumsDto.toArtistAlbums()
            }
        }
    }
}