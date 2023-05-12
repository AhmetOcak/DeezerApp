package com.albumdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.TransparentWhite
import com.model.albumdetail.AlbumSong
import com.ui.FullScreenProgIndicator
import com.ui.SongCard

@Composable
fun AlbumDetailScreen(modifier: Modifier = Modifier) {

    val viewModel: AlbumDetailViewModel = hiltViewModel()

    val albumDetailsState by viewModel.albumDetailsState.collectAsState()

    AlbumDetailScreenContent(
        modifier = modifier,
        albumDetailsState = albumDetailsState,
        albumName = viewModel.albumName
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumDetailScreenContent(
    modifier: Modifier,
    albumDetailsState: AlbumDetailsState,
    albumName: String
) {
    Scaffold(
        topBar = {
            DeezerTopAppBar(
                title = albumName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = TransparentWhite)
            )
        }
    ) {
        when (albumDetailsState) {
            is AlbumDetailsState.Loading -> {
                FullScreenProgIndicator()
            }

            is AlbumDetailsState.Success -> {
                AlbumImage(
                    modifier = modifier
                        .fillMaxWidth()
                        .height(256.dp),
                    albumImageUrl = albumDetailsState.data.coverBig
                )
                SongList(
                    modifier = modifier,
                    songs = albumDetailsState.data.tracks.data
                )
            }

            is AlbumDetailsState.Error -> {

            }
        }
    }
}

@Composable
private fun SongList(modifier: Modifier, songs: ArrayList<AlbumSong>) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 256.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
        items(songs, key = { it.id }) {
            SongCard(
                modifier = Modifier,
                songImageUrl = it.album.coverBig,
                songName = it.title,
                duration = "${it.duration / 60}",
                onSongClicked = {},
                onFavouriteBtnClicked = {},
                favoriteIconInitVal = false
            )
        }
    }
}

@Composable
private fun AlbumImage(modifier: Modifier, albumImageUrl: String) {
    Box(
        modifier = modifier
    ) {
        AnimatedImage(
            modifier = modifier.fillMaxSize(),
            imageUrl = albumImageUrl
        )
    }
}