package com.ahmetocak.network.datasource

import com.ahmetocak.network.model.ArtistDetailDto
import com.ahmetocak.network.model.ArtistDto
import com.ahmetocak.network.model.MusicGenreDto
import com.ahmetocak.network.model.albumdetail.AlbumDetailsDto

interface RemoteDeezerDataSource {

    suspend fun getAlbumDetails(albumId: Long): AlbumDetailsDto

    suspend fun getArtists(genreId: Long): ArtistDto

    suspend fun getArtistDetails(artistId: Long): ArtistDetailDto

    suspend fun getMusicGenres(): MusicGenreDto
}