package com.model.albumdetail

data class AlbumSong(
    val id: Int,
    val readable: Boolean,
    val title: String,
    val titleShort: String,
    val titleVersion: String,
    val link: String,
    val duration: Int,
    val rank: Int,
    val explicitLyrics: Boolean,
    val explicitContentLyrics: Int,
    val explicitContentCover: Int,
    val preview: String,
    val md5Image: String,
    val artist: AlbumArtists,
    val album: Detail,
    val type: String
)
