package com.ahmetocak.network.model.albumdetail

import com.ahmetocak.network.model.albumdetail.AlbumGenreDataDto
import com.google.gson.annotations.SerializedName

data class AlbumGenresDto(
    @SerializedName("data")
    val data: ArrayList<AlbumGenreDataDto> = arrayListOf()
)