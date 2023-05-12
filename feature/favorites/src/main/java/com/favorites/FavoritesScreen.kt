package com.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.HeartRed
import com.ui.MusicPlayer
import com.ui.SongCard

private val HEART_SIZE = 196.dp

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier) {

    FavoritesScreenContent(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreenContent(modifier: Modifier) {

    var showMusicPlayer by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopBar() }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LikesBoard(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize()
            )
            Divider()
            FavoriteSongs(
                modifier = modifier
                    .weight(4f)
                    .fillMaxSize(),
                onSongClicked = {
                    if (!showMusicPlayer) {
                        showMusicPlayer = true
                    }
                }
            )
        }
        if (showMusicPlayer) {
            MusicPlayer(
                songName = "Gel içelim",
                songArtist = "Duman",
                onCloseClicked = { showMusicPlayer = false },
                onPlayButtonClicked = {}
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar() {
    DeezerTopAppBar(
        title = "Favorites",
        navigationIcon = DeezerIcons.ArrowBack,
        navigationContentDescription = null,
        onNavigateClick = {}
    )
}

@Composable
private fun LikesBoard(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.size(HEART_SIZE),
            imageVector = DeezerIcons.Favorite,
            contentDescription = null,
            tint = HeartRed
        )
    }
}

@Composable
private fun FavoriteSongs(modifier: Modifier, onSongClicked: () -> Unit) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            SongCard(
                modifier = Modifier,
                songImageUrl = "https://e-cdns-images.dzcdn.net/images/artist/50c9aca4265d49bc492fb29d2b824aea/500x500-000000-80-0-0.jpg",
                songName = "Gel İçelim",
                duration = "2:54",
                favoriteIconInitVal = true,
                onSongClicked = onSongClicked,
                onFavouriteBtnClicked = {},
                showAlbum = true,
                albumName = "Duman II"
            )
        }
    }
}