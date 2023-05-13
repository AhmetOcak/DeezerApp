package com.deezerapp.navigation

sealed class NavScreen(val route: String) {
    object MusicGenres : NavScreen(route = NavRoutes.music_genres)
    object Artists : NavScreen(route = NavRoutes.artists_screen)
    object ArtistDetail : NavScreen(route = NavRoutes.artist_detail_screen)
    object AlbumDetails : NavScreen(route = NavRoutes.album_details_screen)
    object Favorites : NavScreen(route = NavRoutes.favorites_screen)
}
