package com.usecases.favorites

import android.util.Log
import com.favoritesongs.repository.IFavoriteSongRepository
import com.models.FavoriteSongs
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val repository: IFavoriteSongRepository) {

    suspend operator fun invoke(): Flow<Response<List<FavoriteSongs>>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getAllFavoriteSongs()))
        } catch (e: Exception) {
            Log.e("GetAllFavoriteSongsUseCase", e.stackTraceToString())
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
        }
    }
}