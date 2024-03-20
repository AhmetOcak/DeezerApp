package com.ahmetocak.domain.albumdetail

import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.AlbumDetails
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.domain.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(albumId: Long): Flow<Response<AlbumDetails>> =
        apiCall { repository.getAlbumDetails(albumId) }
}