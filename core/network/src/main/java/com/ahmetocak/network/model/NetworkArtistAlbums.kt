package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class NetworkArtistAlbums(
    @SerializedName("data")
    val data: ArrayList<NetworkAlbums> = arrayListOf(),

    @SerializedName("total")
    val total: Int? = null,

    @SerializedName("next")
    val next: String? = null,

    @SerializedName("prev")
    val prev: String? = null
)

data class NetworkAlbums(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("cover_big")
    val image: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null
)