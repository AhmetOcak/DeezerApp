package com.model

data class FavoriteSongs(
    val id: Long,
    val songImgUrl: String,
    val songName: String,
    val albumName: String,
    val duration: Int
)
