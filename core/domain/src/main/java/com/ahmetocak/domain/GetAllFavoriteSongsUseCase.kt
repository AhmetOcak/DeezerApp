package com.ahmetocak.domain

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.domain.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val repository: DeezerRepository) {

    operator fun invoke(): Response<Flow<List<FavoriteSongs>>> {
        return try {
            Response.Success(data = repository.getAllFavoriteSongs())
        } catch (e: Exception) {
            Response.Error(errorMessageId = R.string.unknown_error)
        }
    }
}