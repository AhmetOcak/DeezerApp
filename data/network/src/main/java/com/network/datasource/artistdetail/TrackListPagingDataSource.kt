package com.network.datasource.artistdetail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.network.model.TrackDataDto
import com.network.retrofit.ArtistDetailApi

class TrackListPagingDataSource(
    private val api: ArtistDetailApi,
    private val artistId: Int
) : PagingSource<Int, TrackDataDto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrackDataDto> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = api.getArtistTrackList(
                artistId = artistId,
                page = page
            )

            LoadResult.Page(
                data = response.data,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(20),
                nextKey = if (response.data.isEmpty()) null else page.plus(20)
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TrackDataDto>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 10
    }
}