package com.models

data class ArtistDetail(
    val id: Long,
    val name: String,
    val link: String,
    val share: String,
    val picture: String,
    val pictureSmall: String,
    val pictureMedium: String,
    val pictureBig: String,
    val pictureXl: String,
    val album: Int,
    val fan: Int,
    val radio: Boolean,
    val trackList: String,
    val type: String
)
