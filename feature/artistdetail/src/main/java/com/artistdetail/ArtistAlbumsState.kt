package com.artistdetail

import com.model.ArtistAlbums

sealed interface ArtistAlbumsState {
    object Loading: ArtistAlbumsState
    data class Success(val data: ArtistAlbums): ArtistAlbumsState
    data class Error(val message: String): ArtistAlbumsState
}