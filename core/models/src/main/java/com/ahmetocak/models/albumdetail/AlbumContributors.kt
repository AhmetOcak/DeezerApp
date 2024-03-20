package com.ahmetocak.models.albumdetail

data class AlbumContributors(
    val id: Long,
    val name: String,
    val link: String,
    val share: String,
    val picture: String,
    val pictureSmall: String,
    val pictureMedium: String,
    val pictureBig: String,
    val pictureXl: String,
    val radio: Boolean,
    val tracklist: String,
    val type: String,
    val role: String
)
