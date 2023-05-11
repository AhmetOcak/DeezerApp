package com.artists

import com.model.ArtistData

sealed interface ArtistState {
    object Loading : ArtistState
    data class Success(val data: ArrayList<ArtistData>) : ArtistState
    data class Error(val message: String) : ArtistState
}