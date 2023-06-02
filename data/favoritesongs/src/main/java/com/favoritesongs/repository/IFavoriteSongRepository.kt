package com.favoritesongs.repository

import com.models.FavoriteSongs


interface IFavoriteSongRepository {

    suspend fun getAllFavoriteSongs(): List<FavoriteSongs>

    suspend fun removeFavoriteSong(songId: Long)
}