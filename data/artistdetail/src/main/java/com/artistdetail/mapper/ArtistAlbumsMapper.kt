package com.artistdetail.mapper

import com.model.ArtistAlbums
import com.model.ArtistAlbumsData
import com.network.model.ArtistAlbumsDto

fun ArtistAlbumsDto.toArtistAlbums(): ArtistAlbums {
    return ArtistAlbums(
        data = data.map {
            ArtistAlbumsData(
                id = it.id ?: -1,
                title = it.title ?: "",
                link = it.link ?: "",
                cover = it.cover ?: "",
                coverSmall = it.coverSmall ?: "",
                coverMedium = it.coverMedium ?: "",
                coverBig = it.coverBig ?: "",
                coverXl = it.coverXl ?: "",
                md5Image = it.md5Image ?: "",
                genreId = it.genreId ?: -1,
                fans = it.fans ?: -1,
                releaseDate = it.releaseDate ?: "",
                recordType = it.recordType ?: "",
                trackList = it.trackList ?: "",
                explicitLyrics = it.explicitLyrics ?: false,
                type = it.type ?: "",
            )
        } as ArrayList<ArtistAlbumsData>,
        total = total ?: 0
    )
}