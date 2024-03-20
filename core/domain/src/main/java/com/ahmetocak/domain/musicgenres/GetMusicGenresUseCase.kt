package com.ahmetocak.domain.musicgenres

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.MusicGenre
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicGenresUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(): Flow<Response<MusicGenre>> =
        apiCall { repository.getMusicGenres() }
}