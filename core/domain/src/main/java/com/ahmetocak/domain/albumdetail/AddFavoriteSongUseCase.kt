package com.ahmetocak.domain.albumdetail

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.dbCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteSongUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(favoriteSongs: FavoriteSongs): Flow<Response<Unit>> =
        dbCall { repository.addFavoriteSong(favoriteSongs) }
}