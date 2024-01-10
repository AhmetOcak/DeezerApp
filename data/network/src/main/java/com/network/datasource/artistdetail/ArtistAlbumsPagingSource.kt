package com.network.datasource.artistdetail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.network.model.AlbumsDto
import com.network.retrofit.ArtistDetailApi
import javax.inject.Inject

class ArtistAlbumsPagingSource @Inject constructor(
    private val artistId: Long,
    val api: ArtistDetailApi
) : PagingSource<Int, AlbumsDto>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AlbumsDto> {
        return try {
            val nextPageIndex = params.key ?: 0
            val response = api.getArtistAlbums(artistId, nextPageIndex)
            LoadResult.Page(
                data = response.data,
                prevKey = null,
                nextKey = if (response.data.size < 25) {
                    null
                } else {
                    nextPageIndex.plus(25)
                }
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, AlbumsDto>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(25)
        }
    }
}