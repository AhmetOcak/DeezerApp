package com.albumdetail

import com.models.albumdetail.AlbumDetails


sealed interface AlbumDetailsState {
    object Loading : AlbumDetailsState
    data class Success(val data: AlbumDetails) : AlbumDetailsState
    data class Error(val message: String) : AlbumDetailsState
}