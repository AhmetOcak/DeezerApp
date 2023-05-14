package com.model

data class ArtistAlbums(
    val data: ArrayList<ArtistAlbumsData>,
    val total: Int
)

data class ArtistAlbumsData(
    val id: Int,
    val title: String,
    val link: String,
    val cover: String,
    val coverSmall: String,
    val coverMedium: String,
    val coverBig: String,
    val coverXl: String,
    val md5Image: String,
    val genreId: Int,
    val fans: Int,
    val releaseDate: String,
    val recordType: String,
    val trackList: String,
    val explicitLyrics: Boolean,
    val type: String
)
