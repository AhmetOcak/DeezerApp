package com.network.datasource.artist

import com.network.model.ArtistDto
import com.network.retrofit.ArtistApi
import javax.inject.Inject

class ArtistRemoteDataSourceImpl @Inject constructor(
    private val api: ArtistApi
) : IArtistRemoteDataSource {

    override suspend fun getArtists(genreId: Long): ArtistDto = api.getArtists(genreId)
}