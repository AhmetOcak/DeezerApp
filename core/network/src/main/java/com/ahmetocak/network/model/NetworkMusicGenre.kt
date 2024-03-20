package com.ahmetocak.network.model

import com.google.gson.annotations.SerializedName

data class NetworkMusicGenre(
    @SerializedName("data")
    val data: ArrayList<NetworkMusicGenreData> = arrayListOf()
)

data class NetworkMusicGenreData(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("picture_medium")
    val image: String? = null
)
