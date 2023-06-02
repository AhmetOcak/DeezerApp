package com.models

data class Artist(
    val data: ArrayList<ArtistData>
)

data class ArtistData(
    val id: Long,
    val name: String,
    val picture: String,
    val pictureSmall: String,
    val pictureMedium: String,
    val pictureBig: String,
    val pictureXl: String,
    val radio: Boolean,
    val trackList: String,
    val type: String
)
