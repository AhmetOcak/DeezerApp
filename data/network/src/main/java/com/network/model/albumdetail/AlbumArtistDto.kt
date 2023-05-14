package com.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class AlbumArtistDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

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

    @SerializedName("tracklist")
    val tracklist: String? = null,

    @SerializedName("type")
    val type: String? = null
)