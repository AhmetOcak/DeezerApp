package com.artistdetail.mapper

import com.models.ArtistAlbums
import com.network.model.AlbumsDto

fun AlbumsDto.toArtistAlbums(): ArtistAlbums {
    return ArtistAlbums(
        id = id ?: 0,
        title = title ?: "",
        link = link ?: "",
        cover = cover ?: "",
        coverSmall = coverSmall ?: "",
        coverMedium = coverMedium ?: "",
        coverBig = coverBig ?: "",
        coverXl = coverXl ?: "",
        md5Image = md5Image ?: "",
        genreId = genreId ?: 0,
        fans = fans ?: 0,
        releaseDate = releaseDate ?: "",
        recordType = recordType ?: "",
        trackList = trackList ?: "",
        explicitLyrics = explicitLyrics ?: false,
        type = type ?: ""
    )
}