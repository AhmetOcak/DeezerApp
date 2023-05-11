package com.musicgenre.repository

import com.model.MusicGenre

interface IMusicGenreRepository {

    suspend fun getMusicGenres(): MusicGenre
}