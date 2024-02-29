package com.ahmetocak.network.model.albumdetail

import com.ahmetocak.network.model.albumdetail.AlbumSongDto
import com.google.gson.annotations.SerializedName

data class AlbumTracksDto(
    @SerializedName("data")
    val data: ArrayList<AlbumSongDto> = arrayListOf()
)