package com.usecases.albumdetail

import com.ahmetocak.data.repository.DeezerRepository
import com.models.albumdetail.AlbumDetails
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(albumId: Long): Flow<Response<AlbumDetails>> =
        apiCall { repository.getAlbumDetails(albumId) }
}