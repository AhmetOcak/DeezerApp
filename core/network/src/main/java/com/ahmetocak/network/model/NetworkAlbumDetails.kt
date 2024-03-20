package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class NetworkAlbumDetails(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("cover_big")
    val image: String? = null,

    @SerializedName("tracks")
    val tracks: NetworkAlbumTracks? = null
)

data class NetworkAlbumTracks(
    @SerializedName("data")
    val data: ArrayList<NetworkAlbumSongs> = arrayListOf()
)

data class NetworkAlbumSongs(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("duration")
    val duration: Int? = null,

    @SerializedName("preview")
    val preview: String? = null,

    @SerializedName("artist")
    val artist: NetworkAlbumArtists? = null,

    @SerializedName("album")
    val album: NetworkAlbumSongDetail? = null
)

data class NetworkAlbumSongDetail(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("cover_big")
    val image: String? = null,
)

data class NetworkAlbumArtists(

    @SerializedName("name")
    val name: String? = null,
)