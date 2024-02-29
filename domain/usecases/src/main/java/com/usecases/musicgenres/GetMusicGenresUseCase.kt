package com.usecases.musicgenres

import com.ahmetocak.data.repository.DeezerRepository
import com.models.MusicGenre
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicGenresUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(): Flow<Response<MusicGenre>> =
        apiCall { repository.getMusicGenres() }
}