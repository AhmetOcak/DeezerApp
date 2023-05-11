package com.artist.repository

import com.model.Artist

interface IArtistRepository {

    suspend fun getArtists(genreId: Int): Artist
}