package com.ahmetocak.network.model.albumdetail

import com.google.gson.annotations.SerializedName

data class AlbumDetailsDto(
    @SerializedName("id")
    val id: Long? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("upc")
    val upc: String? = null,

    @SerializedName("link")
    val link: String? = null,

    @SerializedName("share")
    val share: String? = null,

    @SerializedName("cover")
    val cover: String? = null,

    @SerializedName("cover_small")
    val coverSmall: String? = null,

    @SerializedName("cover_medium")
    val coverMedium: String? = null,

    @SerializedName("cover_big")
    val coverBig: String? = null,

    @SerializedName("cover_xl")
    val coverXl: String? = null,

    @SerializedName("md5_image")
    val md5Image: String? = null,

    @SerializedName("genre_id")
    val genreId: Int? = null,

    @SerializedName("genres")
    val genres: AlbumGenresDto? = null,

    @SerializedName("label")
    val label: String? = null,

    @SerializedName("nb_tracks")
    val nbTracks: Int? = null,

    @SerializedName("duration")
    val duration: Int? = null,

    @SerializedName("fans")
    val fans: Int? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("record_type")
    val recordType: String? = null,

    @SerializedName("available")
    val available: Boolean? = null,

    @SerializedName("tracklist")
    val tracklist: String? = null,

    @SerializedName("explicit_lyrics")
    val explicitLyrics: Boolean? = null,

    @SerializedName("explicit_content_lyrics")
    val explicitContentLyrics: Int? = null,

    @SerializedName("explicit_content_cover")
    val explicitContentCover: Int? = null,

    @SerializedName("contributors")
    val contributors: ArrayList<AlbumContributorsDto>,

    @SerializedName("artist")
    val artist: AlbumArtistDto? = null,

    @SerializedName("type")
    val type: String? = null,

    @SerializedName("tracks")
    val tracks: AlbumTracksDto? = null
)