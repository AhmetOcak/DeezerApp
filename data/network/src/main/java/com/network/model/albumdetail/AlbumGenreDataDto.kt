package com.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class AlbumGenreDataDto(
    @SerializedName("id")
    val id: Int? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("picture")
    val picture: String? = null,

    @SerializedName("type")
    val type: String? = null
)