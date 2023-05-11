package com.deezerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.albumdetail.AlbumDetailScreen
import com.artistdetail.ArtistDetailScreen
import com.artists.ArtistsScreen
import com.deezerapp.ui.theme.DeezerAppTheme
import com.favorites.FavoritesScreen
import com.musicgenres.MusicGenresScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeezerAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MusicGenresScreen()
                }
            }
        }
    }
}