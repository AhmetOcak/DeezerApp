package com.artistdetail

import com.model.ArtistDetail

sealed interface ArtistDetailState {
    object Loading : ArtistDetailState
    data class Success(val data: ArtistDetail) : ArtistDetailState
    data class Error(val message: String) : ArtistDetailState
}