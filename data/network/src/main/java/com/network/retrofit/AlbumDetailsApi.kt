package com.network.retrofit

import com.network.EndPoint
import com.network.model.albumdetail.AlbumDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumDetailsApi {

    @GET(EndPoint.ALBUM_DETAIL)
    suspend fun getAlbumDetails(
        @Path("album_id") albumId: Int
    ) : AlbumDetailsDto
}