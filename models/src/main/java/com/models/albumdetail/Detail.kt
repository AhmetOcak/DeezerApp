package com.models.albumdetail

data class Detail(
    val id: Long,
    val title: String,
    val cover: String,
    val coverSmall: String,
    val coverMedium: String,
    val coverBig: String,
    val coverXl: String,
    val md5Image: String,
    val tracklist: String,
    val type: String
)
