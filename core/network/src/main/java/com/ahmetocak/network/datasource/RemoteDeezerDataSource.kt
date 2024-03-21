package com.ahmetocak.network.datasource

import com.ahmetocak.common.Response
import com.ahmetocak.network.model.NetworkArtistDetail
import com.ahmetocak.network.model.NetworkArtist
import com.ahmetocak.network.model.NetworkMusicGenre
import com.ahmetocak.network.model.NetworkAlbumDetails

interface RemoteDeezerDataSource {

    suspend fun getAlbumDetails(albumId: Long): Response<NetworkAlbumDetails>

    suspend fun getArtists(genreId: Long): Response<NetworkArtist>

    suspend fun getArtistDetails(artistId: Long): Response<NetworkArtistDetail>

    suspend fun getMusicGenres(): Response<NetworkMusicGenre>
}