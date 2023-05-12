package com.albumdetail

import com.model.albumdetail.*
import com.network.model.albumdetail.AlbumDetailsDto

internal fun AlbumDetailsDto.toAlbumDetails(): AlbumDetails {
    return AlbumDetails(
        id = id ?: 0,
        title = title ?: "",
        upc = upc ?: "",
        link = link ?: "",
        share = share ?: "",
        cover = cover ?: "",
        coverSmall = coverSmall ?: "",
        coverMedium = coverMedium ?: "",
        coverBig = coverBig ?: "",
        coverXl = coverXl ?: "",
        md5Image = md5Image ?: "",
        genreId = genreId ?: -1,
        genres = AlbumGenres(
            data = genres?.data?.map {
                AlbumGenreData(
                    id = it.id ?: -1,
                    name = it.name ?: "",
                    picture = it.picture ?: "",
                    type = it.type ?: "",
                )
            } as ArrayList<AlbumGenreData>
        ),
        label = label ?: "",
        nbTracks = nbTracks ?: 0,
        duration = duration ?: 0,
        fans = fans ?: 0,
        releaseDate = releaseDate ?: "",
        recordType = recordType ?: "",
        available = available ?: false,
        tracklist = tracklist ?: "",
        explicitLyrics = explicitLyrics ?: false,
        explicitContentLyrics = explicitContentLyrics ?: 0,
        explicitContentCover = explicitContentCover ?: 0,
        contributors = contributors.map {
            AlbumContributors(
                id = it.id ?: -1,
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
        } as ArrayList<AlbumContributors>,
        artist = AlbumArtist(
            id = artist?.id ?: -1,
            name = artist?.name ?: "",
            picture = artist?.picture ?: "",
            pictureSmall = artist?.pictureSmall ?: "",
            pictureMedium = artist?.pictureMedium ?: "",
            pictureBig = artist?.pictureBig ?: "",
            pictureXl = artist?.pictureXl ?: "",
            tracklist = artist?.tracklist ?: "",
            type = artist?.type ?: "",
        ),
        type = type ?: "",
        tracks = AlbumTracks(
            data = tracks?.data?.map {
                AlbumSong(
                    id = it.id ?: -1,
                    readable = it.readable ?: false,
                    title = it.title ?: "",
                    titleShort = it.titleShort ?: "",
                    titleVersion = it.titleVersion ?: "",
                    link = it.link ?: "",
                    duration = it.duration ?: 0,
                    rank = it.rank ?: 0,
                    explicitLyrics = explicitLyrics ?: false,
                    explicitContentLyrics = explicitContentLyrics ?: 0,
                    explicitContentCover = explicitContentCover ?: 0,
                    preview = it.preview ?: "",
                    md5Image = it.md5Image ?: "",
                    artist = AlbumArtists(
                        id = it.artist?.id ?: -1,
                        name = it.artist?.name ?: "",
                        tracklist = it.artist?.tracklist ?: "",
                        type = it.artist?.type ?: ""
                    ),
                    album = Detail(
                        id = it.album?.id ?: -1,
                        title = it.album?.title ?: "",
                        cover = it.album?.cover ?: "",
                        coverSmall = it.album?.coverSmall ?: "",
                        coverMedium = it.album?.coverMedium ?: "",
                        coverBig = it.album?.coverBig ?: "",
                        coverXl = it.album?.coverXl ?: "",
                        md5Image = it.album?.md5Image ?: "",
                        tracklist = it.album?.tracklist ?: "",
                        type = it.album?.type ?: ""
                    ),
                    type = it.type ?: ""
                )
            } as ArrayList<AlbumSong>
        )
    )
}