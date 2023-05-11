package com.artistdetail

import com.model.Album
import com.model.ArtistInfo
import com.model.Contributors
import com.model.TrackData
import com.network.model.TrackDataDto

fun TrackDataDto.toTrackData(): TrackData {
    return TrackData(
        id = id ?: 0,
        readable = readable ?: false,
        title = title ?: "",
        titleShort = titleShort ?: "",
        link = link ?: "",
        duration = duration ?: 0,
        rank = rank ?: 0,
        explicitLyrics = explicitLyrics ?: false,
        explicitContentLyrics = explicitContentLyrics ?: 0,
        explicitContentCover = explicitContentCover ?: 0,
        preview = preview ?: "",
        contributors = contributors.map {
            Contributors(
                id = it.id ?: 0,
                name = it.name ?: "",
                link = it.link ?: "",
                share = it.share ?: "",
                picture = it.picture ?: "",
                pictureSmall = it.pictureSmall ?: "",
                pictureMedium = it.pictureMedium ?: "",
                pictureBig = it.pictureBig ?: "",
                pictureXl = it.pictureXl ?: "",
                radio = it.radio ?: false,
                tracklist = it.tracklist ?: "",
                type = it.type ?: "",
                role = it.role ?: "",
            )
        } as ArrayList<Contributors>,
        image = image ?: "",
        artist = ArtistInfo(
            id = artist?.id ?: 0,
            name = artist?.name ?: "",
            tracklist = artist?.tracklist ?: "",
            type = artist?.type ?: "",
        ),
        album = Album(
            id = album?.id ?: 0,
            title = album?.title ?: "",
            cover = album?.cover ?: "",
            coverSmall = album?.coverSmall ?: "",
            coverMedium = album?.coverMedium ?: "",
            coverBig = album?.coverBig ?: "",
            coverXl = album?.coverXl ?: "",
            md5Image = album?.md5Image ?: "",
            tracklist = album?.tracklist ?: "",
            type = album?.type ?: "",
        ),
        type = type ?: "",
    )
}