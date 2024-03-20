package com.ahmetocak.network.retrofit

import com.ahmetocak.network.EndPoint
import com.ahmetocak.network.model.NetworkArtistAlbums
import com.ahmetocak.network.model.NetworkArtistDetail
import com.ahmetocak.network.model.NetworkArtist
import com.ahmetocak.network.model.NetworkMusicGenre
import com.ahmetocak.network.model.NetworkAlbumDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApi {

    @GET(EndPoint.ALBUM_DETAIL)
    suspend fun getAlbumDetails(
        @Path("album_id") albumId: Long
    ) : NetworkAlbumDetails

    @GET(EndPoint.ARTIST)
    suspend fun getArtists(
        @Path("genre_id") genreId: Long
    ) : NetworkArtist

    @GET(EndPoint.ARTIST_DETAIL)
    suspend fun getArtistDetails(
        @Path("artist_id") artistId: Long
    ) : NetworkArtistDetail

    @GET(EndPoint.ARTIST_TRACK_LIST)
    suspend fun getArtistAlbums(
        @Path("artist_id") artistId: Long,
        @Query("index") index: Int
    ) : NetworkArtistAlbums

    @GET(EndPoint.MUSIC_GENRE)
    suspend fun getMusicGenres(): NetworkMusicGenre
}