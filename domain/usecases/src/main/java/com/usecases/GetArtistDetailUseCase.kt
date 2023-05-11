package com.usecases

import android.util.Log
import com.artistdetail.repository.IArtistDetailRepository
import com.model.ArtistDetail
import com.usecases.common.NO_INTERNET
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetArtistDetailUseCase @Inject constructor(private val repository: IArtistDetailRepository) {

    suspend operator fun invoke(artistId: Int): Flow<Response<ArtistDetail>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getArtistDetails(artistId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = NO_INTERNET))
            Log.e("GetArtistDetailUseCase", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
            Log.e("GetArtistDetailUseCase", e.stackTraceToString())
        }
    }
}