package com.favorites

import com.model.FavoriteSongs

sealed interface FavoritesState {
    object Nothing: FavoritesState
    object Loading: FavoritesState
    data class Success(val data: List<FavoriteSongs>): FavoritesState
    data class Error(val message: String): FavoritesState
}