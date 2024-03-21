package com.ahmetocak.database.datasource

import com.ahmetocak.common.Response
import com.ahmetocak.database.entity.FavoriteSongsEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteSongsLocalDataSource {

    suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity): Response<Unit>

    suspend fun getAllFavoriteSongs(): Response<Flow<List<FavoriteSongsEntity>>>

    suspend fun removeFavoriteSong(songId: Long): Response<Unit>
}