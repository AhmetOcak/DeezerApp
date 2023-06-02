package com.favoritesongs.repository

import com.database.datasource.IFavoriteSongsLocalDataSource
import com.favoritesongs.toFavoriteSongsList
import com.models.FavoriteSongs
import javax.inject.Inject

class FavoriteSongRepositoryImpl @Inject constructor(
    private val localDataSource: IFavoriteSongsLocalDataSource
) : IFavoriteSongRepository {

    override suspend fun getAllFavoriteSongs(): List<FavoriteSongs> {
        return localDataSource.getAllFavoriteSongs().toFavoriteSongsList()
    }

    override suspend fun removeFavoriteSong(songId: Long) {
        return localDataSource.removeFavoriteSong(songId)
    }
}