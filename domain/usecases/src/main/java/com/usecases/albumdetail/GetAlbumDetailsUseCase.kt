package com.usecases.albumdetail

import com.albumdetail.repository.IAlbumDetailRepository
import com.models.albumdetail.AlbumDetails
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val repository: IAlbumDetailRepository) {

    suspend operator fun invoke(albumId: Long): Flow<Response<AlbumDetails>> =
        apiCall { repository.getAlbumDetails(albumId) }
}