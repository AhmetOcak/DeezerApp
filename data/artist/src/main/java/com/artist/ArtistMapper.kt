package com.artist

import com.model.Artist
import com.model.ArtistData
import com.network.model.ArtistDto

internal fun ArtistDto.toArtist(): Artist {
    return Artist(
        data = data.map {
            ArtistData(
                id = it.id ?: 0,
                name = it.name ?: "",
                picture = it.picture ?: "",
                pictureSmall = it.pictureSmall ?: "",
                pictureMedium = it.pictureMedium ?: "",
                pictureBig = it.pictureBig ?: "",
                pictureXl = it.pictureXl ?: "",
                radio = it.radio ?: false,
                trackList = it.trackList ?: "",
                type = it.type ?: ""
            )
        } as ArrayList<ArtistData>
    )
}