package com.usecases.artistdetail

import com.ahmetocak.data.repository.DeezerRepository
import com.models.ArtistDetail
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistDetailUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(artistId: Long): Flow<Response<ArtistDetail>> =
        apiCall { repository.getArtistDetails(artistId) }
}