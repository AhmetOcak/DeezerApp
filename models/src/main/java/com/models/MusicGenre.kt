package com.models

data class MusicGenre(
    val data: ArrayList<Data>
)

data class Data(
    val id: Long,
    val name: String,
    val picture: String,
    val pictureSmall: String,
    val pictureMedium: String,
    val pictureBig: String,
    val pictureXl: String,
    val type: String
)
