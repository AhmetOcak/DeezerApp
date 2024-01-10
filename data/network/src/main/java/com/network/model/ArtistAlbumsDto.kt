package com.network.model

import com.google.gson.annotations.SerializedName

data class ArtistAlbumsDto(
    @SerializedName("data")
    val data: ArrayList<AlbumsDto> = arrayListOf(),

    @SerializedName("total")
    val total: Int? = null,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("prev")
    val prev: String? = null
)

data class AlbumsDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("link")
    val link: String? = null,

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

    @SerializedName("genre_id")
    val genreId: Int? = null,

    @SerializedName("fans")
    val fans: Int? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("recode_type")
    val recordType: String? = null,

    @SerializedName("tracklist")
    val trackList: String? = null,

    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean? = null,

    @SerializedName("type")
    val type: String? = null
)