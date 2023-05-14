package com.network.model

import com.google.gson.annotations.SerializedName

data class ArtistDetailDto(
    @SerializedName("id")
    val id: Long? = null,

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

    @SerializedName("nb_album")
    val album: Int? = null,

    @SerializedName("nb_fan")
    val fan: Int? = null,

    @SerializedName("radio")
    val radio: Boolean? = null,

    @SerializedName("tracklist")
    val trackList: String? = null,

    @SerializedName("type")
    val type: String? = null
)
