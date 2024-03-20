package com.ahmetocak.models

data class Artist(
    val data: ArrayList<ArtistData>
)

data class ArtistData(
    val id: Long,
    val name: String,
    val image: String
)
