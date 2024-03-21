package com.ahmetocak.domain

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.FavoriteSongs
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(): Response<Flow<List<FavoriteSongs>>> =
        repository.getAllFavoriteSongs()

}