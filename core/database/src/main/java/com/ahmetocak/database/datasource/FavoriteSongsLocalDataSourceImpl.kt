package com.ahmetocak.database.datasource

import com.ahmetocak.common.Response
import com.ahmetocak.database.dao.FavoriteSongsDao
import com.ahmetocak.database.dbCall
import com.ahmetocak.database.entity.FavoriteSongsEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoriteSongsLocalDataSourceImpl @Inject constructor(
    private val dao: FavoriteSongsDao
) : FavoriteSongsLocalDataSource {

    override suspend fun addFavoriteSong(favoriteSongsEntity: FavoriteSongsEntity) =
        dbCall { dao.addFavoriteSong(favoriteSongsEntity) }

    override suspend fun getAllFavoriteSongs(): Response<Flow<List<FavoriteSongsEntity>>> =
        dbCall { dao.getAllFavoriteSongs() }

    override suspend fun removeFavoriteSong(songId: Long) =
        dbCall { dao.removeFavoriteSong(songId) }
}