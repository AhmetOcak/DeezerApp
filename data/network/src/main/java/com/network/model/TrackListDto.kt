package com.network.model

import com.google.gson.annotations.SerializedName

data class TrackListDto(
    @SerializedName("data")
    val data: ArrayList<TrackDataDto> = arrayListOf(),

    @SerializedName("total")
    val total: Int? = null,

    @SerializedName("next")
    val next: String? = null
)

data class ContributorsDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("link")
    val link: String? = null,

    @SerializedName("share")
    val share: String? = null,

    @SerializedName("picture")
    val picture: String? = null,

    @SerializedName("picture_small")
    val pictureSmall: String? = null,

    @SerializedName("picture_medium")
    val pictureMedium: String? = null,

    @SerializedName("picture_big")
    val pictureBig: String? = null,

    @SerializedName("picture_xl")
    val pictureXl: String? = null,

    @SerializedName("radio")
    val radio: Boolean? = null,

    @SerializedName("tracklist")
    val tracklist: String? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("role")
    val role: String? = null
)

data class ArtistInfoDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("tracklist")
    val tracklist: String? = null,

    @SerializedName("type")
    val type: String? = null
)

data class AlbumDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("cover")
    val cover: String? = null,

    @SerializedName("cover_small")
    val coverSmall: String? = null,

    @SerializedName("cover_medium")
    val coverMedium: String? = null,

    @SerializedName("cover_big")
    val coverBig: String? = null,

    @SerializedName("cover_xl")
    val coverXl: String? = null,

    @SerializedName("md5_image")
    val md5Image: String? = null,

    @SerializedName("tracklist")
    val tracklist: String? = null,

    @SerializedName("type")
    val type: String? = null
)

data class TrackDataDto(
    @SerializedName("id")
    val id: Int? = null,

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

    @SerializedName("contributors")
    val contributors: ArrayList<ContributorsDto> = arrayListOf(),

    @SerializedName("md5_image")
    val image: String? = null,

    @SerializedName("artist")
    val artist: ArtistInfoDto? = null,

    @SerializedName("album")
    val album: AlbumDto? = null,

    @SerializedName("type")
    val type: String? = null
)