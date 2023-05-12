package com.albumdetail.repository

import com.model.albumdetail.AlbumDetails

interface IAlbumDetailRepository {

    suspend fun getAlbumDetails(albumId: Int): AlbumDetails
}