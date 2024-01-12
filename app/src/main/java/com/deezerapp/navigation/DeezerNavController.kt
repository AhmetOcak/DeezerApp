package com.deezerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.deezerapp.utils.encodeForSafe

object MainDestinations {
    const val MUSIC_GENRES_ROUTE = "music_genres_route"
    const val ARTISTS_ROUTE = "artists_route"
    const val ARTIST_DETAIL_ROUTE = "artist_detail_route"
    const val ALBUM_DETAILS_ROUTE = "album_details_route"
    const val FAVORITES_ROUTE = "favorites_route"
    const val GENRE_ID_KEY = "genre_id"
    const val GENRE_NAME_KEY = "genre_name"
    const val ARTIST_ID_KEY = "artist_id"
    const val ALBUM_ID_KEY = "album_id"
    const val PLAY_MUSIC_ROUTE = "play_music_route"
}

@Composable
fun rememberDeezerNavController(
    navController: NavHostController = rememberNavController()
): DeezerNavController = remember(navController) {
    DeezerNavController(navController)
}

@Stable
class DeezerNavController(val navController: NavHostController) {

    fun upPress() {
        navController.navigateUp()
    }

    fun navigateToArtists(id: Long, name: String, from: NavBackStackEntry) {
        if (shouldNavigate(from)) {
            navController.navigate("${MainDestinations.ARTISTS_ROUTE}/$id/${encodeForSafe(name)}")
        }
    }

    fun navigateFavorites(from: NavBackStackEntry) {
        if (shouldNavigate(from)) {
            navController.navigate(MainDestinations.FAVORITES_ROUTE)
        }
    }

    fun navigateArtistDetail(id: Long, from: NavBackStackEntry) {
        if (shouldNavigate(from)) {
            navController.navigate("${MainDestinations.ARTIST_DETAIL_ROUTE}/$id")
        }
    }

    fun navigateAlbumDetails(id: Long, from: NavBackStackEntry) {
        if (shouldNavigate(from)) {
            navController.navigate("${MainDestinations.ALBUM_DETAILS_ROUTE}/$id")
        }
    }

    fun navigatePlayMusic(from: NavBackStackEntry) {
        if (shouldNavigate(from)) {
            navController.navigate(MainDestinations.PLAY_MUSIC_ROUTE)
        }
    }
}

private fun shouldNavigate(from: NavBackStackEntry): Boolean = from.lifecycleIsResumed()

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED