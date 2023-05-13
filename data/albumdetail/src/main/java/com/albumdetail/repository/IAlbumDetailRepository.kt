package com.albumdetail.repository

import com.model.FavoriteSongs
import com.model.albumdetail.AlbumDetails

interface IAlbumDetailRepository {

    suspend fun getAlbumDetails(albumId: Int): AlbumDetails

    suspend fun getAllFavoriteSongs(): List<FavoriteSongs>

    suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs)

    suspend fun removeFavoriteSong(songId: Int)
}