package com.artistdetail

import com.model.ArtistDetail
import com.network.model.ArtistDetailDto

fun ArtistDetailDto.toArtistDetail(): ArtistDetail {
    return ArtistDetail(
        id = id ?: 0,
        name = name ?: "",
        link = link ?: "",
        share = share ?: "",
        picture = picture ?: "",
        pictureSmall = pictureSmall ?: "",
        pictureMedium = pictureMedium ?: "",
        pictureBig = pictureBig ?: "",
        pictureXl = pictureXl ?: "",
        album = album ?: 0,
        fan = fan ?: 0,
        radio = radio ?: false,
        trackList = trackList ?: "",
        type = type ?: ""
    )
}

