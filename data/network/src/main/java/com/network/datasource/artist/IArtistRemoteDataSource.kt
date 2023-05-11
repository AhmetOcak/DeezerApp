package com.network.datasource.artist

import com.network.model.ArtistDto

interface IArtistRemoteDataSource {

    suspend fun getArtists(genreId: Int): ArtistDto
}