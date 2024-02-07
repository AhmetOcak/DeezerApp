package com.deezerapp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.albumdetail.AlbumDetailScreen
import com.artistdetail.ArtistDetailScreen
import com.artists.ArtistsScreen
import com.deezerapp.navigation.MainDestinations
import com.deezerapp.navigation.rememberDeezerNavController
import com.designsystem.utils.Music
import com.favorites.FavoritesScreen
import com.musicgenres.MusicGenresScreen
import com.playmusic.PlayMusicScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DeezerApp() {
    val deezerNavController = rememberDeezerNavController()
    NavHost(
        navController = deezerNavController.navController,
        startDestination = MainDestinations.MUSIC_GENRES_ROUTE
    ) {
        deezerGraph(
            onFavoritesClick = deezerNavController::navigateFavorites,
            onMusicGenreClick = deezerNavController::navigateToArtists,
            onArtistClick = deezerNavController::navigateArtistDetail,
            onAlbumClick = deezerNavController::navigateAlbumDetails,
            onSongClick = deezerNavController::navigatePlayMusic,
            upPress = deezerNavController::upPress
        )
    }
}

private fun NavGraphBuilder.deezerGraph(
    onFavoritesClick: (NavBackStackEntry) -> Unit,
    onMusicGenreClick: (Long, String, NavBackStackEntry) -> Unit,
    onArtistClick: (Long, NavBackStackEntry) -> Unit,
    onAlbumClick: (Long, NavBackStackEntry) -> Unit,
    onSongClick: (Music, NavBackStackEntry) -> Unit,
    upPress: () -> Unit
) {
    composable(route = MainDestinations.MUSIC_GENRES_ROUTE) { from ->
        MusicGenresScreen(
            onMusicGenreClick = remember { { id, name -> onMusicGenreClick(id, name, from) } },
            onFavoritesClick = remember { { onFavoritesClick(from) } }
        )
    }
    composable(
        route = "${MainDestinations.ARTISTS_ROUTE}/{${MainDestinations.GENRE_ID_KEY}}/{${MainDestinations.GENRE_NAME_KEY}}",
        arguments = listOf(
            navArgument(MainDestinations.GENRE_ID_KEY) { type = NavType.LongType },
            navArgument(MainDestinations.GENRE_NAME_KEY) { type = NavType.StringType }
        )
    ) { from ->
        val genreName = from.arguments?.getString(MainDestinations.GENRE_NAME_KEY)
        ArtistsScreen(
            onArtistClick = remember { { id -> onArtistClick(id, from) } },
            upPress = remember { { upPress() } },
            genreName = URLDecoder.decode(genreName, StandardCharsets.UTF_8.toString())
        )
    }
    composable(
        route = "${MainDestinations.ARTIST_DETAIL_ROUTE}/{${MainDestinations.ARTIST_ID_KEY}}",
        arguments = listOf(
            navArgument(MainDestinations.ARTIST_ID_KEY) { type = NavType.LongType }
        )
    ) { from ->
        ArtistDetailScreen(
            onArtistClick = remember { { id -> onAlbumClick(id, from) } },
            upPress = remember { { upPress() } }
        )
    }
    composable(
        route = "${MainDestinations.ALBUM_DETAILS_ROUTE}/{${MainDestinations.ALBUM_ID_KEY}}",
        arguments = listOf(
            navArgument(MainDestinations.ALBUM_ID_KEY) { type = NavType.LongType }
        )
    ) { from ->
        AlbumDetailScreen(
            upPress = remember { { upPress() } },
            onSongClicked = remember { {
                onSongClick(it, from)
            } }
        )
    }
    composable(route = MainDestinations.FAVORITES_ROUTE) { from ->
        FavoritesScreen(
            upPress = remember { { upPress() } },
            onSongClicked = remember { {
                onSongClick(it, from)
            } }
        )
    }
    composable(
        route = MainDestinations.PLAY_MUSIC_ROUTE +
                "/{${MainDestinations.PLAY_MUSIC_IMG_KEY}}" +
                "/{${MainDestinations.PLAY_MUSIC_NAME_KEY}}" +
                "/{${MainDestinations.PLAY_MUSIC_ARTIST_KEY}}" +
                "/{${MainDestinations.PLAY_MUSIC_AUDIO_URL_KEY}}"
    ) {
        PlayMusicScreen(upPress = remember { { upPress() } })
    }
}