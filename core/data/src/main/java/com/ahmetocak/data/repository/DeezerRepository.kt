package com.ahmetocak.data.repository

import androidx.paging.PagingData
import com.ahmetocak.common.Response
import com.ahmetocak.models.Artist
import com.ahmetocak.models.ArtistAlbums
import com.ahmetocak.models.ArtistDetail
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.models.MusicGenre
import com.ahmetocak.models.AlbumDetails
import kotlinx.coroutines.flow.Flow

interface DeezerRepository {

    suspend fun getAlbumDetails(albumId: Long): Response<AlbumDetails>

    suspend fun getAllFavoriteSongs(): Response<Flow<List<FavoriteSongs>>>

    suspend fun addFavoriteSong(favoriteSongs: FavoriteSongs): Response<Unit>

    suspend fun removeFavoriteSong(songId: Long): Response<Unit>

    suspend fun getArtists(genreId: Long): Response<Artist>

    suspend fun getArtistDetails(artistId: Long): Response<ArtistDetail>

    fun getArtistAlbums(artistId: Long): Flow<PagingData<ArtistAlbums>>

    suspend fun getMusicGenres(): Response<MusicGenre>
}