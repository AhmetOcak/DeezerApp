package com.ahmetocak.data.repository

import androidx.paging.PagingData
import com.models.Artist
import com.models.ArtistAlbums
import com.models.ArtistDetail
import com.models.FavoriteSongs
import com.models.MusicGenre
import com.models.albumdetail.AlbumDetails
import kotlinx.coroutines.flow.Flow

interface DeezerRepository {

    suspend fun getAlbumDetails(albumId: Long): AlbumDetails

    suspend fun getAllFavoriteSongs(): List<FavoriteSongs>

    suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs)

    suspend fun removeFavoriteSong(songId: Long)

    suspend fun getArtists(genreId: Long): Artist

    suspend fun getArtistDetails(artistId: Long): ArtistDetail

    fun getArtistAlbums(artistId: Long): Flow<PagingData<ArtistAlbums>>

    suspend fun getMusicGenres(): MusicGenre
}