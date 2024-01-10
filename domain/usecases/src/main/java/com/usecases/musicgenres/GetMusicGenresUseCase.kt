package com.usecases.musicgenres

import com.models.MusicGenre
import com.musicgenre.repository.IMusicGenreRepository
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMusicGenresUseCase @Inject constructor(private val repository: IMusicGenreRepository) {

    suspend operator fun invoke(): Flow<Response<MusicGenre>> =
        apiCall { repository.getMusicGenres() }
}