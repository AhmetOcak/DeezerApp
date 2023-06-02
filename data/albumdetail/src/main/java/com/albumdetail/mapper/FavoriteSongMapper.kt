package com.albumdetail.mapper

import com.database.entity.FavoriteSongsEntity
import com.models.FavoriteSongs

fun FavoriteSongs.toFavoriteSongsEntity(): FavoriteSongsEntity {
    return FavoriteSongsEntity(
        id = id,
        songName = songName,
        songImgUrl = songImgUrl,
        duration = duration,
        artistName = artistName,
        audioUrl = audioUrl,
        albumName = albumName
    )
}

fun List<FavoriteSongsEntity>.toFavoriteSongsList(): List<FavoriteSongs> {
    val favoriteSongs = mutableListOf<FavoriteSongs>()

    forEach {
        favoriteSongs.add(
            FavoriteSongs(
                id = it.id,
                songName = it.songName,
                songImgUrl = it.songImgUrl,
                duration = it.duration,
                artistName = it.artistName,
                audioUrl = it.audioUrl,
                albumName = it.albumName
            )
        )
    }

    return favoriteSongs
}