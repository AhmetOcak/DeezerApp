package com.albumdetail.repository

import com.models.FavoriteSongs
import com.models.albumdetail.AlbumDetails

interface IAlbumDetailRepository {

    suspend fun getAlbumDetails(albumId: Long): AlbumDetails

    suspend fun getAllFavoriteSongs(): List<FavoriteSongs>

    suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs)

    suspend fun removeFavoriteSong(songId: Long)
}