package com.network.datasource.musicgenre

import com.network.model.musicgenre.MusicGenreDto
import com.network.retrofit.MusicGenreApi
import javax.inject.Inject

class MusicGenreRemoteDataSourceImpl @Inject constructor(private val api: MusicGenreApi) : IMusicGenreRemoteDataSource {

    override suspend fun getMusicGenres(): MusicGenreDto = api.getMusicGenres()
}