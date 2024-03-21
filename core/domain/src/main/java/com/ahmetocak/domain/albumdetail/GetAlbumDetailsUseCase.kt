package com.ahmetocak.domain.albumdetail

import com.ahmetocak.common.Response
import com.ahmetocak.data.repository.DeezerRepository
import com.ahmetocak.models.AlbumDetails
import javax.inject.Inject

class GetAlbumDetailsUseCase @Inject constructor(private val repository: DeezerRepository) {

    suspend operator fun invoke(albumId: Long): Response<AlbumDetails> =
        repository.getAlbumDetails(albumId)
}