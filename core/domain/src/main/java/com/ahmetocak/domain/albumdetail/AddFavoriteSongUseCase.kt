package com.ahmetocak.domain.albumdetail

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.FavoriteSongs
import javax.inject.Inject

class AddFavoriteSongUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(favoriteSongs: FavoriteSongs): Response<Unit> =
        repository.addFavoriteSong(favoriteSongs)
}