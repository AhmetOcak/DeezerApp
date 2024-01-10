package com.usecases.artistdetail

import androidx.paging.PagingData
import com.artistdetail.repository.IArtistDetailRepository
import com.models.ArtistAlbums
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(private val repository: IArtistDetailRepository) {

    operator fun invoke(artistId: Long): Flow<PagingData<ArtistAlbums>> =
        repository.getArtistAlbums(artistId)
}