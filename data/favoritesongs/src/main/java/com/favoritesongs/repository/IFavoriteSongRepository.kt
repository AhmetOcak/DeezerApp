package com.favoritesongs.repository

import com.model.FavoriteSongs

interface IFavoriteSongRepository {

    suspend fun getAllFavoriteSongs(): List<FavoriteSongs>

    suspend fun removeFavoriteSong(songId: Int)
}