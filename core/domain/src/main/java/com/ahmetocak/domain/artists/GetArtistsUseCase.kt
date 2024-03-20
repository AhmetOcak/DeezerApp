package com.ahmetocak.domain.artists

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.Artist
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(genreId: Long): Flow<Response<Artist>> =
        apiCall { repository.getArtists(genreId) }
}