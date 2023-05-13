package com.usecases.albumdetail

import android.util.Log
import com.albumdetail.repository.IAlbumDetailRepository
import com.model.albumdetail.AlbumDetails
import com.usecases.common.NO_INTERNET
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val repository: IAlbumDetailRepository) {

    suspend operator fun invoke(albumId: Int): Flow<Response<AlbumDetails>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getAlbumDetails(albumId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = NO_INTERNET))
            Log.e("GetAlbumDetailsUseCase", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
            Log.e("GetAlbumDetailsUseCase", e.stackTraceToString())
        }
    }
}