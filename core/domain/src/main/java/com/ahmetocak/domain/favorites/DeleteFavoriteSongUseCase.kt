package com.ahmetocak.domain.favorites

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.dbCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteSongUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(songId: Long): Flow<Response<Unit>> =
        dbCall { repository.removeFavoriteSong(songId) }
}