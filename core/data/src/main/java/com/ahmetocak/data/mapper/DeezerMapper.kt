package com.ahmetocak.data.mapper

import com.ahmetocak.database.entity.FavoriteSongsEntity
import com.ahmetocak.models.AlbumArtists
import com.ahmetocak.models.Artist
import com.ahmetocak.models.ArtistAlbums
import com.ahmetocak.models.ArtistData
import com.ahmetocak.models.ArtistDetail
import com.ahmetocak.models.MusicGenreDetail
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.models.MusicGenre
import com.ahmetocak.models.AlbumDetails
import com.ahmetocak.models.AlbumSong
import com.ahmetocak.models.AlbumSongDetail
import com.ahmetocak.models.AlbumTracks
import com.ahmetocak.network.model.NetworkAlbums
import com.ahmetocak.network.model.NetworkArtistDetail
import com.ahmetocak.network.model.NetworkArtist
import com.ahmetocak.network.model.NetworkMusicGenre
import com.ahmetocak.network.model.NetworkAlbumDetails

internal fun NetworkAlbumDetails.toAlbumDetails(): AlbumDetails {
    return AlbumDetails(
        id = id ?: 0,
        title = title ?: "",
        image = image ?: "",
        tracks = AlbumTracks(
            songs = tracks?.data?.map {
                AlbumSong(
                    id = it.id ?: -1,
                    title = it.title ?: "",
                    duration = it.duration ?: 0,
                    preview = it.preview ?: "",
                    artist = AlbumArtists(
                        name = it.artist?.name ?: "",
                    ),
                    album = AlbumSongDetail(
                        id = it.album?.id ?: -1,
                        title = it.album?.title ?: "",
                        image = it.album?.image ?: ""
                    ),
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

internal fun NetworkArtist.toArtist(): Artist {
    return Artist(
        data = data.map {
            ArtistData(
                id = it.id ?: 0,
                name = it.name ?: "",
                image = it.image ?: ""
            )
        } as ArrayList<ArtistData>
    )
}

internal fun NetworkAlbums.toArtistAlbums(): ArtistAlbums {
    return ArtistAlbums(
        id = id ?: 0,
        title = title ?: "",
        image = image ?: "",
        releaseDate = releaseDate ?: ""
    )
}

internal fun NetworkArtistDetail.toArtistDetail(): ArtistDetail {
    return ArtistDetail(
        id = id ?: 0,
        name = name ?: "",
        image = image ?: ""
    )
}

internal fun NetworkMusicGenre.toMusicGenre() : MusicGenre {
    return MusicGenre(
        data = data.map {
            MusicGenreDetail(
                id = it.id ?: 0,
                name = it.name ?: "",
                image = it.image ?: ""
            )
        } as ArrayList<MusicGenreDetail>
    )
}