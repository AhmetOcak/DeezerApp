package com.network.retrofit

import com.network.EndPoint
import com.network.model.ArtistAlbumsDto
import com.network.model.ArtistDetailDto
import retrofit2.http.GET
import retrofit2.http.Path

interface ArtistDetailApi {

    @GET(EndPoint.ARTIST_DETAIL)
    suspend fun getArtistDetails(
        @Path("artist_id") artistId: Long
    ) : ArtistDetailDto

    @GET(EndPoint.ARTIST_TRACK_LIST)
     suspend fun getArtistAlbums(
        @Path("artist_id") artist_id: Long
    ) : ArtistAlbumsDto
}