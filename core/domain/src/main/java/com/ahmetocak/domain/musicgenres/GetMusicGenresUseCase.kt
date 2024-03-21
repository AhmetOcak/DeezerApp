package com.ahmetocak.domain.musicgenres

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.MusicGenre
import javax.inject.Inject

class GetMusicGenresUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(): Response<MusicGenre> = repository.getMusicGenres()
}