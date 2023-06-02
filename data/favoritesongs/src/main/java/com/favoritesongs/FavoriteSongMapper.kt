package com.favoritesongs

import com.database.entity.FavoriteSongsEntity
import com.models.FavoriteSongs

fun FavoriteSongs.toFavoriteSongsEntity(): FavoriteSongsEntity {
    return FavoriteSongsEntity(
        id = id,
        songName = songName,
        songImgUrl = songImgUrl,
        duration = duration,
        albumName = albumName,
        audioUrl = audioUrl,
        artistName = artistName
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
                albumName = it.albumName,
                artistName = it.artistName,
                audioUrl = it.audioUrl
            )
        )
    }

    return favoriteSongs
}