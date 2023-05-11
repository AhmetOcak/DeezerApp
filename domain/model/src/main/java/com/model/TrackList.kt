package com.model

data class TrackList(
    val data: ArrayList<TrackData>,
    val total: Int,
    val next: String
)

data class Contributors(
    val id: Int,
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

data class ArtistInfo(
    val id: Int,
    val name: String,
    val tracklist: String,
    val type: String
)

data class Album(
    val id: Int,
    val title: String,
    val cover: String,
    val coverSmall: String,
    val coverMedium: String,
    val coverBig: String,
    val coverXl: String,
    val md5Image: String,
    val tracklist: String,
    val type: String
)

data class TrackData(
    val id: Int,
    val readable: Boolean,
    val title: String,
    val titleShort: String,
    val link: String,
    val duration: Int,
    val rank: Int,
    val explicitLyrics: Boolean,
    val explicitContentLyrics: Int,
    val explicitContentCover: Int,
    val preview: String,
    val contributors: ArrayList<Contributors>,
    val image: String,
    val artist: ArtistInfo,
    val album: Album,
    val type: String
)