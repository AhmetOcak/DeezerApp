package com.ahmetocak.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class AlbumSongDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("readable")
    val readable: Boolean? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("title_short")
    val titleShort: String? = null,

    @SerializedName("title_version")
    val titleVersion: String? = null,

    @SerializedName("link")
    val link: String? = null,

    @SerializedName("duration")
    val duration: Int? = null,

    @SerializedName("rank")
    val rank: Int? = null,

    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean? = null,

    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int? = null,

    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int? = null,

    @SerializedName("preview")
    val preview: String? = null,

    @SerializedName("md5_image")
    val md5Image: String? = null,

    @SerializedName("artist")
    val artist: AlbumArtistsDto? = null,

    @SerializedName("album")
    val album: DetailDto? = null,

    @SerializedName("type")
    val type: String? = null
)