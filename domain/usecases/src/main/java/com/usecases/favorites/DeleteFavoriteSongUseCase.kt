package com.usecases.favorites

import android.util.Log
import com.favoritesongs.repository.IFavoriteSongRepository
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteFavoriteSongUseCase @Inject constructor(private val repository: IFavoriteSongRepository) {

    suspend operator fun invoke(songId: Long): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(repository.removeFavoriteSong(songId)))
        } catch (e: Exception) {
            Log.e("DeleteFavoriteSongUseCase", e.stackTraceToString())
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
        }
    }
}