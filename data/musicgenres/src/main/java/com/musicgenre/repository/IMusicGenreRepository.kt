package com.musicgenre.repository

import com.models.MusicGenre


interface IMusicGenreRepository {

    suspend fun getMusicGenres(): MusicGenre
}