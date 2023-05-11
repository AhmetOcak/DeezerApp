package com.network.retrofit

import com.network.EndPoint
import com.network.model.musicgenre.MusicGenreDto
import retrofit2.http.GET

interface MusicGenreApi {

    @GET(EndPoint.MUSIC_GENRE_ENDPOINT)
    suspend fun getMusicGenres(): MusicGenreDto
}