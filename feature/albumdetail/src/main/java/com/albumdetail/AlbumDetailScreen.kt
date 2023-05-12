package com.albumdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.model.albumdetail.AlbumSong
import com.ui.FullScreenProgIndicator
import com.ui.MusicPlayer
import com.ui.SongCard

private val ALBUM_IMG_SIZE = 224.dp

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumDetailScreenContent(
    modifier: Modifier,
    albumDetailsState: AlbumDetailsState,
    albumName: String
) {

    var showMusicPlayer by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            DeezerTopAppBar(
                title = albumName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = {}
            )
        }
    ) {
        when (albumDetailsState) {
            is AlbumDetailsState.Loading -> {
                FullScreenProgIndicator()
            }

            is AlbumDetailsState.Success -> {
                Column(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(it)
                ) {
                    AlbumImage(
                        modifier = modifier
                            .weight(2f)
                            .fillMaxSize(),
                        albumImageUrl = albumDetailsState.data.coverBig
                    )
                    Divider(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    SongList(
                        modifier = modifier
                            .weight(3f)
                            .fillMaxSize(),
                        songs = albumDetailsState.data.tracks.data,
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

            is AlbumDetailsState.Error -> {

            }
        }
    }
}

@Composable
private fun AlbumImage(modifier: Modifier, albumImageUrl: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        AnimatedImage(
            modifier = Modifier
                .size(ALBUM_IMG_SIZE)
                .clip(RoundedCornerShape(20)),
            imageUrl = albumImageUrl
        )
    }
}

@Composable
private fun SongList(modifier: Modifier, songs: ArrayList<AlbumSong>, onSongClicked: () -> Unit) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
        items(songs, key = { it.id }) {
            SongCard(
                modifier = Modifier,
                songImageUrl = it.album.coverBig,
                songName = it.title,
                duration = "${it.duration / 60}",
                onSongClicked = onSongClicked,
                onFavouriteBtnClicked = {},
                favoriteIconInitVal = false
            )
        }
    }
}