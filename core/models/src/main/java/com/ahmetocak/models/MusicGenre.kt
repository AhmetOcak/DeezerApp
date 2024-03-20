package com.ahmetocak.models

data class MusicGenre(
    val data: ArrayList<MusicGenreDetail>
)

data class MusicGenreDetail(
    val id: Long,
    val name: String,
    val image: String
)