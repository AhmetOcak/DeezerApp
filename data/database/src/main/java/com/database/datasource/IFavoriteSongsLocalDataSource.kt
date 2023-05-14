package com.database.datasource

import com.database.entity.FavoriteSongsEntity

interface IFavoriteSongsLocalDataSource {

    suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity)

    suspend fun getAllFavoriteSongs(): List<FavoriteSongsEntity>

    suspend fun removeFavoriteSong(songId: Long)
}