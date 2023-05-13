package com.artistdetail

import androidx.paging.PagingData
import com.model.TrackData
import kotlinx.coroutines.flow.Flow

sealed interface AlbumsState {
    object Nothing : AlbumsState
    data class Data(val data: PagingData<TrackData>) : AlbumsState
}