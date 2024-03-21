package com.ahmetocak.domain.artistdetail

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.ArtistDetail
import javax.inject.Inject

class GetArtistDetailUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(artistId: Long): Response<ArtistDetail> =
        repository.getArtistDetails(artistId)
}