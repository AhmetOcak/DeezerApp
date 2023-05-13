package com.deezerapp.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.albumdetail.AlbumDetailScreen
import com.artistdetail.ArtistDetailScreen
import com.artists.ArtistsScreen
import com.favorites.FavoritesScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.musicgenres.MusicGenresScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DeezerNavHost(
    startDestination: String = NavScreen.MusicGenres.route,
    navController: NavHostController = rememberAnimatedNavController()
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(250)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(250)
            )
        }
    ) {
        composable(route = NavScreen.MusicGenres.route) {
            MusicGenresScreen(
                onNavigateArtistsScreen = { id, name ->
                    navController.navigate("${NavNames.artists_screen}/$id/$name")
                },
                onNavigateFavoritesScreen = {
                    navController.navigate(NavScreen.Favorites.route)
                }
            )
        }
        composable(
            route = NavScreen.Artists.route,
            arguments = listOf(
                navArgument(NavArgKeys.artists_screen_arg_key_1) { type = NavType.IntType },
                navArgument(NavArgKeys.artists_screen_arg_key_2) { type = NavType.StringType }
            )
        ) { navBackStackEntry ->
            val genreName =
                navBackStackEntry.arguments?.getString(NavArgKeys.artists_screen_arg_key_2)

            if (genreName != null) {
                ArtistsScreen(
                    onNavigateArtistDetailScreen = {
                        navController.navigate("${NavNames.artist_detail_screen}/$it")
                    },
                    onNavigateBackClicked = {
                        navController.navigateUp()
                    },
                    genreName = genreName
                )
            }
        }
        composable(
            route = NavScreen.ArtistDetail.route,
            arguments = listOf(
                navArgument(NavArgKeys.artist_detail_screen_arg_key) { type = NavType.IntType }
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
                navArgument(NavArgKeys.album_details_screen_arg_key) { type = NavType.IntType }
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