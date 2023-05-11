package com.artist.repository

import com.artist.toArtist
import com.model.Artist
import com.network.datasource.artist.ArtistRemoteDataSourceImpl
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    private val dataSourceImpl: ArtistRemoteDataSourceImpl
) : IArtistRepository {

    override suspend fun getArtists(genreId: Int): Artist {
        return dataSourceImpl.getArtists(genreId).toArtist()
    }
}