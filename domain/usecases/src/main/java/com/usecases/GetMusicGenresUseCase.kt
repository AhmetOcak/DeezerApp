package com.usecases

import com.model.MusicGenre
import com.musicgenre.repository.IMusicGenreRepository
import com.usecases.common.NO_INTERNET
import com.usecases.common.Response
import com.usecases.common.UNKNOWN
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetMusicGenresUseCase @Inject constructor(private val repository: IMusicGenreRepository) {

    suspend operator fun invoke(): Flow<Response<MusicGenre>> = flow {
        try {
            emit(Response.Loading)

            emit(Response.Success(data = repository.getMusicGenres()))
        } catch (e: IOException) {
            emit(Response.Error(errorMessage = NO_INTERNET))
        } catch (e: Exception) {
            emit(Response.Error(errorMessage = e.message ?: UNKNOWN))
        }
    }
}