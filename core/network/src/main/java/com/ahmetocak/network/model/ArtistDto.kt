package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class ArtistDto(
    @SerializedName("data")
    val data: ArrayList<ArtistDataDto> = arrayListOf()
)

data class ArtistDataDto(
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

    @SerializedName("radio")
    val radio: Boolean? = null,

    @SerializedName("tracklist")
    val trackList: String? = null,

    @SerializedName("type")
    val type: String? = null
)
