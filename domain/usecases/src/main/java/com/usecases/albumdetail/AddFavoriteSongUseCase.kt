package com.usecases.albumdetail

import com.ahmetocak.data.repository.DeezerRepository
import com.models.FavoriteSongs
import com.usecases.utils.Response
import com.usecases.utils.dbCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoriteSongUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(favoriteSongs: FavoriteSongs): Flow<Response<Unit>> =
        dbCall { repository.addFavoriteSong(favoriteSongs) }
}