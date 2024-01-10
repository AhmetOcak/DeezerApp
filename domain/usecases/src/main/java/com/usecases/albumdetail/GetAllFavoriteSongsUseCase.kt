package com.usecases.albumdetail

import com.albumdetail.repository.IAlbumDetailRepository
import com.models.FavoriteSongs
import com.usecases.utils.Response
import com.usecases.utils.dbCall
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllFavoriteSongsUseCase @Inject constructor(private val repository: IAlbumDetailRepository) {

    suspend operator fun invoke(): Flow<Response<List<FavoriteSongs>>> =
        dbCall { repository.getAllFavoriteSongs() }
}