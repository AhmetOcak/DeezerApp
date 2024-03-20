package com.ahmetocak.data.repository

import androidx.paging.PagingData
import com.ahmetocak.models.Artist
import com.ahmetocak.models.ArtistAlbums
import com.ahmetocak.models.ArtistDetail
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.models.MusicGenre
import com.ahmetocak.models.AlbumDetails
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