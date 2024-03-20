package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class NetworkArtist(
    @SerializedName("data")
    val data: ArrayList<NetworkArtistData> = arrayListOf()
)

data class NetworkArtistData(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("picture_medium")
    val image: String? = null
)
