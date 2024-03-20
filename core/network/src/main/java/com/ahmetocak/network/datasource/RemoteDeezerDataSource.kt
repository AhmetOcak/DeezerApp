package com.ahmetocak.network.datasource

import com.ahmetocak.network.model.NetworkArtistDetail
import com.ahmetocak.network.model.NetworkArtist
import com.ahmetocak.network.model.NetworkMusicGenre
import com.ahmetocak.network.model.NetworkAlbumDetails

interface RemoteDeezerDataSource {

    suspend fun getAlbumDetails(albumId: Long): NetworkAlbumDetails

    suspend fun getArtists(genreId: Long): NetworkArtist

    suspend fun getArtistDetails(artistId: Long): NetworkArtistDetail

    suspend fun getMusicGenres(): NetworkMusicGenre
}