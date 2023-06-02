package com.usecases.artists

import android.util.Log
import com.artist.repository.IArtistRepository
import com.models.Artist
import com.usecases.common.NO_INTERNET
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val repository: IArtistRepository) {

    suspend operator fun invoke(genreId: Long): Flow<Response<Artist>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getArtists(genreId)))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = NO_INTERNET))
            Log.e("GetArtistsUseCase", e.stackTraceToString())
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
            Log.e("GetArtistsUseCase", e.stackTraceToString())
        }
    }
}