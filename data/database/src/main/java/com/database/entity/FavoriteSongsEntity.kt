package com.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_song")
data class FavoriteSongsEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Long,

    @ColumnInfo(name = "img_url")
    var songImgUrl: String,

    @ColumnInfo(name = "name")
    var songName: String,

    @ColumnInfo(name = "album_name")
    var albumName: String,

    @ColumnInfo(name = "audio_url")
    var audioUrl: String,

    @ColumnInfo(name = "artist_name")
    var artistName: String,

    @ColumnInfo(name = "duration")
    var duration: Int
)
