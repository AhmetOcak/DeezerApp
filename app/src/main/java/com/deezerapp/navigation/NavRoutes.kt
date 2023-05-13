package com.deezerapp.navigation

internal object NavRoutes {
    const val music_genres = "music_genres"
    const val artists_screen = "${NavNames.artists_screen}/{${NavArgKeys.artists_screen_arg_key_1}}/{${NavArgKeys.artists_screen_arg_key_2}}"
    const val artist_detail_screen = "${NavNames.artist_detail_screen}/{${NavArgKeys.artist_detail_screen_arg_key}}"
    const val album_details_screen = "${NavNames.album_details_screen}/{${NavArgKeys.album_details_screen_arg_key}}"
    const val favorites_screen = "favorites_screen"
}

internal object NavNames {
    const val artists_screen = "artists_screen"
    const val artist_detail_screen = "artist_detail_screen"
    const val album_details_screen = "album_details_screen"
}

internal object NavArgKeys {
    const val artists_screen_arg_key_1 = "genre_id"
    const val artists_screen_arg_key_2 = "genre_name"
    const val artist_detail_screen_arg_key = "artist_id"
    const val album_details_screen_arg_key = "album_id"
}