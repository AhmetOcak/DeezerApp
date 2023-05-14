package com.network

internal object BaseUrl {
    const val DEEZER = "https://api.deezer.com"
}

internal object EndPoint {
    const val MUSIC_GENRE = "/genre"
    const val ARTIST = "/genre/{genre_id}/artists"
    const val ARTIST_DETAIL = "/artist/{artist_id}"
    const val ARTIST_TRACK_LIST = "/artist/{artist_id}/albums"
    const val ALBUM_DETAIL = "/album/{album_id}"
}