package com.usecases

import android.util.Log
import com.albumdetail.repository.IAlbumDetailRepository
import com.model.FavoriteSongs
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddFavoriteSongUseCase @Inject constructor(private val repository: IAlbumDetailRepository) {

    suspend operator fun invoke(favoriteSongs: FavoriteSongs): Flow<Response<Unit>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(repository.addFavoriteSong(favoriteSongs)))
        } catch (e: Exception) {
            Log.e("AddFavoriteSongUseCase", e.stackTraceToString())
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
        }
    }
}