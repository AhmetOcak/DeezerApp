package com.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class DetailDto(
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