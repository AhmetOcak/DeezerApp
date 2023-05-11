package com.musicgenre

import com.model.Data
import com.model.MusicGenre
import com.network.model.musicgenre.MusicGenreDto

internal fun MusicGenreDto.toMusicGenre() : MusicGenre {
    return MusicGenre(
        data = data.map {
            Data(
                id = it.id ?: 0,
                name = it.name ?: "",
                picture = it.picture ?: "",
                pictureSmall = it.pictureSmall ?: "",
                pictureMedium = it.pictureMedium ?: "",
                pictureBig = it.pictureBig ?: "",
                pictureXl = it.pictureXl ?: "",
                type = it.type ?: ""
            )
        } as ArrayList<Data>
    )
}