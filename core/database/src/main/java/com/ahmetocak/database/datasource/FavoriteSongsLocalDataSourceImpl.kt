package com.ahmetocak.database.datasource

import com.ahmetocak.database.dao.FavoriteSongsDao
import com.ahmetocak.database.entity.FavoriteSongsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteSongsLocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteSongsDao
) : FavoriteSongsLocalDataSource {

    override suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity) =
        dao.addFavoriteSong(favoriteSongsEntity)

    override fun getAllFavoriteSongs(): Flow<List<FavoriteSongsEntity>> =
        dao.getAllFavoriteSongs()

    override suspend fun removeFavoriteSong(songId: Long) =
        dao.removeFavoriteSong(songId)
}