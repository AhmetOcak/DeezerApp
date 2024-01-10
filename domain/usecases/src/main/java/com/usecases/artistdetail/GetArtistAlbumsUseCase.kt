package com.usecases.artistdetail

import com.artistdetail.repository.IArtistDetailRepository
import com.models.ArtistAlbums
import com.usecases.utils.Response
import com.usecases.utils.apiCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(private val repository: IArtistDetailRepository) {

    suspend operator fun invoke(artistId: Long): Flow<Response<ArtistAlbums>> =
        apiCall { repository.getArtistAlbums(artistId) }
}