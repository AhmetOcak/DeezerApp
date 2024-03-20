package com.ahmetocak.network.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ahmetocak.network.model.NetworkAlbums
import com.ahmetocak.network.retrofit.DeezerApi
import javax.inject.Inject

class ArtistAlbumsPagingSource @Inject constructor(
    private val artistId: Long,
    private val api: DeezerApi
) : PagingSource<Int, NetworkAlbums>() {

    override val keyReuseSupported: Boolean
        get() = true

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NetworkAlbums> {
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

    override fun getRefreshKey(state: PagingState<Int, NetworkAlbums>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.nextKey?.minus(25)
        }
    }
}