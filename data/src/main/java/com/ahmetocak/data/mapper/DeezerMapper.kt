package com.ahmetocak.data.mapper

import com.ahmetocak.database.entity.FavoriteSongsEntity
import com.ahmetocak.network.model.AlbumsDto
import com.ahmetocak.network.model.ArtistDetailDto
import com.ahmetocak.network.model.ArtistDto
import com.ahmetocak.network.model.MusicGenreDto
import com.ahmetocak.network.model.albumdetail.AlbumDetailsDto
import com.models.Artist
import com.models.ArtistAlbums
import com.models.ArtistData
import com.models.ArtistDetail
import com.models.Data
import com.models.FavoriteSongs
import com.models.MusicGenre
import com.models.albumdetail.AlbumArtist
import com.models.albumdetail.AlbumArtists
import com.models.albumdetail.AlbumContributors
import com.models.albumdetail.AlbumDetails
import com.models.albumdetail.AlbumGenreData
import com.models.albumdetail.AlbumGenres
import com.models.albumdetail.AlbumSong
import com.models.albumdetail.AlbumTracks
import com.models.albumdetail.Detail

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

internal fun FavoriteSongs.toFavoriteSongsEntity(): FavoriteSongsEntity {
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

internal fun List<FavoriteSongsEntity>.toFavoriteSongsList(): List<FavoriteSongs> {
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

internal fun AlbumsDto.toArtistAlbums(): ArtistAlbums {
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

internal fun ArtistDetailDto.toArtistDetail(): ArtistDetail {
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