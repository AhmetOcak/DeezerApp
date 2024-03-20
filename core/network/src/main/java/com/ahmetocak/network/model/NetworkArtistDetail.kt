package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class NetworkArtistDetail(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("picture_big")
    val image: String? = null
)
