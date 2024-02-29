package com.usecases.artists

import com.ahmetocak.data.repository.DeezerRepository
import com.models.Artist
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(genreId: Long): Flow<Response<Artist>> =
        apiCall { repository.getArtists(genreId) }
}