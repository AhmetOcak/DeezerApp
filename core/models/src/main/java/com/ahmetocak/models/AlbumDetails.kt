package com.ahmetocak.models

data class AlbumDetails(
    val id: Long,
    val title: String,
    val image: String,
    val tracks: AlbumTracks
)

data class AlbumTracks(
    val songs: ArrayList<AlbumSong> = arrayListOf()
)

data class AlbumSong(
    val id: Long,
    val title: String,
    val duration: Int,
    val preview: String,
    val artist: AlbumArtists,
    val album: AlbumSongDetail
)

data class AlbumSongDetail(
    val id: Long,
    val title: String,
    val image: String
)

data class AlbumArtists(
    val name: String
)