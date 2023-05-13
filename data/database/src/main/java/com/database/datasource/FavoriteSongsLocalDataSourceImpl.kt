package com.database.datasource

import com.database.dao.FavoriteSongsDao
import com.database.entity.FavoriteSongsEntity
import javax.inject.Inject

class FavoriteSongsLocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteSongsDao
) : IFavoriteSongsLocalDataSource {

    override suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity) =
        dao.addFavoriteSong(favoriteSongsEntity)

    override suspend fun getAllFavoriteSongs(): List<FavoriteSongsEntity> =
        dao.getAllFavoriteSongs()

    override suspend fun removeFavoriteSong(songId: Int) =
        dao.removeFavoriteSong(songId)
}