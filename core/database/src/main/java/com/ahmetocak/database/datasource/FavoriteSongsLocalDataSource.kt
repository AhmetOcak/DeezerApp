package com.ahmetocak.database.datasource

import com.ahmetocak.database.entity.FavoriteSongsEntity

interface FavoriteSongsLocalDataSource {

    suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity)

    suspend fun getAllFavoriteSongs(): List<FavoriteSongsEntity>

    suspend fun removeFavoriteSong(songId: Long)
}