package com.usecases.artistdetail

import androidx.paging.PagingData
import com.artistdetail.repository.IArtistDetailRepository
import com.model.TrackData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrackListUseCase @Inject constructor(private val repository: IArtistDetailRepository) {

    operator fun invoke(artistId: Int): Flow<PagingData<TrackData>> {
        return repository.getTrackList(artistId)
    }
}