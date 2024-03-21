package com.ahmetocak.domain.artists

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.Artist
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(genreId: Long): Response<Artist> = repository.getArtists(genreId)
}