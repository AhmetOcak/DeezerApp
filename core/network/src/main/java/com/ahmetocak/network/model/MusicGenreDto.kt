package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class MusicGenreDto(
    @SerializedName("data")
    val data: ArrayList<DataDto> = arrayListOf()
)

data class DataDto(
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

    @SerializedName("type")
    val type: String? = null
)
