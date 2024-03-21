package com.ahmetocak.domain.favorites

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import javax.inject.Inject

class DeleteFavoriteSongUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(songId: Long): Response<Unit> =
        repository.removeFavoriteSong(songId)
}