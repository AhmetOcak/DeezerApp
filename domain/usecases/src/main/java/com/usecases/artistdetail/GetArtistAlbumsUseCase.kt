package com.usecases.artistdetail

import android.util.Log
import com.artistdetail.repository.IArtistDetailRepository
import com.models.ArtistAlbums
import com.usecases.common.NO_INTERNET
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(private val repository: IArtistDetailRepository) {

    suspend operator fun invoke(artistId: Long): Flow<Response<ArtistAlbums>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getArtistAlbums(artistId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = NO_INTERNET))
            Log.e("GetArtistAlbumsUseCase", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
            Log.e("GetArtistAlbumsUseCase", e.stackTraceToString())
        }
    }
}