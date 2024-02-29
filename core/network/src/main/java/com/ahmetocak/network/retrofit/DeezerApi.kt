package com.ahmetocak.network.retrofit

import com.ahmetocak.network.EndPoint
import com.ahmetocak.network.model.ArtistAlbumsDto
import com.ahmetocak.network.model.ArtistDetailDto
import com.ahmetocak.network.model.ArtistDto
import com.ahmetocak.network.model.MusicGenreDto
import com.ahmetocak.network.model.albumdetail.AlbumDetailsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApi {

    @GET(EndPoint.ALBUM_DETAIL)
    suspend fun getAlbumDetails(
        @Path("album_id") albumId: Long
    ) : AlbumDetailsDto

    @GET(EndPoint.ARTIST)
    suspend fun getArtists(
        @Path("genre_id") genreId: Long
    ) : ArtistDto

    @GET(EndPoint.ARTIST_DETAIL)
    suspend fun getArtistDetails(
        @Path("artist_id") artistId: Long
    ) : ArtistDetailDto

    @GET(EndPoint.ARTIST_TRACK_LIST)
    suspend fun getArtistAlbums(
        @Path("artist_id") artistId: Long,
        @Query("index") index: Int
    ) : ArtistAlbumsDto

    @GET(EndPoint.MUSIC_GENRE)
    suspend fun getMusicGenres(): MusicGenreDto
}