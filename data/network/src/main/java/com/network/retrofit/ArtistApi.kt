package com.network.retrofit

import com.network.EndPoint
import com.network.model.ArtistDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistApi {

    @GET(EndPoint.ARTIST)
    suspend fun getArtists(
        @Path("genre_id") genreId: Long
    ) : ArtistDto
}