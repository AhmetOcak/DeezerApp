package com.deezerapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.albumdetail.AlbumDetailScreen
import com.artistdetail.ArtistDetailScreen
import com.artists.ArtistsScreen
import com.deezerapp.helpers.encodeForSafe
import com.favorites.FavoritesScreen
import com.musicgenres.MusicGenresScreen
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun DeezerNavHost(
    startDestination: String = NavScreen.MusicGenres.route,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavScreen.MusicGenres.route) {
            MusicGenresScreen(
                onNavigateArtistsScreen = { id, name ->
                    navController.navigate("${NavNames.artists_screen}/$id/${encodeForSafe(name)}")
                },
                onNavigateFavoritesScreen = {
                    navController.navigate(NavScreen.Favorites.route)
                }
            )
        }
        composable(
            route = NavScreen.Artists.route,
            arguments = listOf(
                navArgument(NavArgKeys.artists_screen_arg_key_1) { type = NavType.LongType },
                navArgument(NavArgKeys.artists_screen_arg_key_2) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val genreName = navBackStackEntry.arguments?.getString(NavArgKeys.artists_screen_arg_key_2)

            if (genreName != null) {
                ArtistsScreen(
                    onNavigateArtistDetailScreen = {
                        navController.navigate("${NavNames.artist_detail_screen}/$it")
                    },
                    onNavigateBackClicked = {
                        navController.navigateUp()
                    },
                    genreName = URLDecoder.decode(genreName, StandardCharsets.UTF_8.toString())
                )
            }
        }
        composable(
            route = NavScreen.ArtistDetail.route,
            arguments = listOf(
                navArgument(NavArgKeys.artist_detail_screen_arg_key) { type = NavType.LongType }
            )
        ) {
            ArtistDetailScreen(
                onNavigateAlbumDetails = {
                    navController.navigate("${NavNames.album_details_screen}/$it")
                },
                onNavigateBackClicked = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = NavScreen.AlbumDetails.route,
            arguments = listOf(
                navArgument(NavArgKeys.album_details_screen_arg_key) { type = NavType.LongType }
            )
        ) {
            AlbumDetailScreen(
                onNavigateBackClicked = {
                    navController.navigateUp()
                }
            )
        }
        composable(route = NavScreen.Favorites.route) {
            FavoritesScreen(
                onNavigateBackClicked = {
                    navController.navigateUp()
                }
            )
        }
    }
}