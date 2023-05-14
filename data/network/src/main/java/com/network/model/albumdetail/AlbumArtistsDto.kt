package com.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class AlbumArtistsDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("tracklist")
    val tracklist: String? = null,

    @SerializedName("type")
    val type: String? = null
)