package com.network.retrofit

import com.network.EndPoint
import com.network.model.ArtistDetailDto
import com.network.model.TrackListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ArtistDetailApi {

    @GET(EndPoint.ARTIST_DETAIL)
    suspend fun getArtistDetails(
        @Path("artist_id") artistId: Int
    ) : ArtistDetailDto

    // https://api.deezer.com/artist/8354140/top?limit=10&index=0
    @GET(EndPoint.ARTIST_TRACK_LIST)
     suspend fun getArtistTrackList(
        @Path("artist_id") artistId: Int,
        @Query("index") page: Int
    ) : TrackListDto
}