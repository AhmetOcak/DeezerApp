package com.ahmetocak.network.datasource

import com.ahmetocak.network.model.ArtistDetailDto
import com.ahmetocak.network.model.ArtistDto
import com.ahmetocak.network.model.MusicGenreDto
import com.ahmetocak.network.model.albumdetail.AlbumDetailsDto
import com.ahmetocak.network.retrofit.DeezerApi
import javax.inject.Inject

class RemoteDeezerDataSourceImpl @Inject constructor(private val api: DeezerApi): RemoteDeezerDataSource {

    override suspend fun getAlbumDetails(albumId: Long): AlbumDetailsDto =
        api.getAlbumDetails(albumId)

    override suspend fun getArtists(genreId: Long): ArtistDto = api.getArtists(genreId)

    override suspend fun getArtistDetails(artistId: Long): ArtistDetailDto =
        api.getArtistDetails(artistId)

    override suspend fun getMusicGenres(): MusicGenreDto = api.getMusicGenres()
}