package com.ahmetocak.models

data class FavoriteSongs(
    val id: Long,
    val songImgUrl: String,
    val songName: String,
    val audioUrl: String,
    val albumName: String,
    val artistName: String,
    val duration: Int
)
