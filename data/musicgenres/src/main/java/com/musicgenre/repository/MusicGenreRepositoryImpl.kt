package com.musicgenre.repository

import com.model.MusicGenre
import com.musicgenre.toMusicGenre
import com.network.datasource.musicgenre.IMusicGenreRemoteDataSource
import javax.inject.Inject

class MusicGenreRepositoryImpl @Inject constructor(
    private val musicGenreRemoteDataSource: IMusicGenreRemoteDataSource
) : IMusicGenreRepository {

    override suspend fun getMusicGenres(): MusicGenre {
        return musicGenreRemoteDataSource.getMusicGenres().toMusicGenre()
    }

}