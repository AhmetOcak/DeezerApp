package com.artist.repository

import com.models.Artist


interface IArtistRepository {

    suspend fun getArtists(genreId: Long): Artist
}