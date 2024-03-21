package com.ahmetocak.network.datasource

import com.ahmetocak.common.Response
import com.ahmetocak.network.apiCall
import com.ahmetocak.network.model.NetworkArtistDetail
import com.ahmetocak.network.model.NetworkArtist
import com.ahmetocak.network.model.NetworkMusicGenre
import com.ahmetocak.network.model.NetworkAlbumDetails
import com.ahmetocak.network.retrofit.DeezerApi
import javax.inject.Inject

class RemoteDeezerDataSourceImpl @Inject constructor(private val api: DeezerApi) :
    RemoteDeezerDataSource {

    override suspend fun getAlbumDetails(albumId: Long): Response<NetworkAlbumDetails> =
        apiCall { api.getAlbumDetails(albumId) }

    override suspend fun getArtists(genreId: Long): Response<NetworkArtist> =
        apiCall { api.getArtists(genreId) }

    override suspend fun getArtistDetails(artistId: Long): Response<NetworkArtistDetail> =
        apiCall { api.getArtistDetails(artistId) }

    override suspend fun getMusicGenres(): Response<NetworkMusicGenre> =
        apiCall { api.getMusicGenres() }
}