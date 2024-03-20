package com.ahmetocak.domain.favorites

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.dbCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(): Flow<Response<List<FavoriteSongs>>> =
        dbCall { repository.getAllFavoriteSongs() }
}