package com.ahmetocak.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class AlbumTracksDto(
    @SerializedName("data")
    val data: ArrayList<AlbumSongDto> = arrayListOf()
)