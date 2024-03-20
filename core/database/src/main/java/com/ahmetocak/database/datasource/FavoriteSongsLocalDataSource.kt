package com.ahmetocak.database.datasource

import com.ahmetocak.database.entity.FavoriteSongsEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteSongsLocalDataSource {

    suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity)

    fun getAllFavoriteSongs(): Flow<List<FavoriteSongsEntity>>

    suspend fun removeFavoriteSong(songId: Long)
}