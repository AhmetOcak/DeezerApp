package com.network.datasource.musicgenre

import com.network.model.musicgenre.MusicGenreDto

interface IMusicGenreRemoteDataSource {

    suspend fun getMusicGenres(): MusicGenreDto
}