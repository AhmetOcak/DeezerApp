package com.ahmetocak.domain.artistdetail

import androidx.paging.PagingData
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.ArtistAlbums
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArtistAlbumsUseCase @Inject constructor(private val repository: DeezerRepository) {

    operator fun invoke(artistId: Long): Flow<PagingData<ArtistAlbums>> =
        repository.getArtistAlbums(artistId)
}