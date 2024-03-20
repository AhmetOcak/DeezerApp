package com.ahmetocak.domain.artistdetail

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.ArtistDetail
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistDetailUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(artistId: Long): Flow<Response<ArtistDetail>> =
        apiCall { repository.getArtistDetails(artistId) }
}