package com.usecases.favorites

import com.favoritesongs.repository.IFavoriteSongRepository
import com.usecases.utils.Response
import com.usecases.utils.dbCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteFavoriteSongUseCase @Inject constructor(private val repository: IFavoriteSongRepository) {

    suspend operator fun invoke(songId: Long): Flow<Response<Unit>> =
        dbCall { repository.removeFavoriteSong(songId) }
}