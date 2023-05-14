package com.favorites

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.HeartRed
import com.ui.EmptyListBox
import com.ui.FullScreenProgIndicator
import com.ui.GIF_HEIGHT
import com.ui.Gif
import com.ui.MusicPlayer
import com.ui.PlayerHeight
import com.ui.SongCard
import kotlin.time.Duration.Companion.seconds

private val HEART_SIZE = 196.dp

@Composable
fun FavoritesScreen(modifier: Modifier = Modifier, onNavigateBackClicked: () -> Unit) {

    val viewModel: FavoritesViewModel = hiltViewModel()

    val deleteState by viewModel.deleteState.collectAsState()

    ShowDeleteMessage(deleteState, viewModel)

    FavoritesScreenContent(
        modifier = modifier,
        onNavigateBackClicked = onNavigateBackClicked,
        viewModel = viewModel,
        onFavouriteBtnClicked = { viewModel.removeFavoriteSong(it) },
        getAllFavoriteSongs = viewModel::getAllFavoriteSongs,
        onPlayAudio = { viewModel.playAudio(it) },
        onPauseAudio = viewModel::pauseAudio,
        onDestroyAudio = viewModel::closeMediaPlayer
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FavoritesScreenContent(
    modifier: Modifier,
    onNavigateBackClicked: () -> Unit,
    viewModel: FavoritesViewModel,
    onFavouriteBtnClicked: (Long) -> Unit,
    getAllFavoriteSongs: () -> Unit,
    onPlayAudio: (String) -> Unit,
    onPauseAudio: () -> Unit,
    onDestroyAudio: () -> Unit
) {
    var showMusicPlayer by rememberSaveable { mutableStateOf(false) }

    var playingArtistName by remember { mutableStateOf("") }
    var playingSongName by remember { mutableStateOf("") }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopBar(onNavigateBackClicked) }
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
            Title()
            Row(modifier.height(GIF_HEIGHT)) {
                if (viewModel.isAudioPlaying) {
                    Gif(context = LocalContext.current)
                }
            }
            Divider()
            FavoriteSongsList(
                modifier = modifier
                    .weight(4f)
                    .fillMaxSize()
                    .padding(bottom = if (showMusicPlayer) PlayerHeight else 0.dp),
                onSongClicked = { musicUrl, artistName, songName ->
                    onPlayAudio(musicUrl)

                    playingArtistName = artistName
                    playingSongName = songName

                    if (!showMusicPlayer) {
                        showMusicPlayer = true
                    }
                },
                favoritesState = viewModel.favoritesState.collectAsState().value,
                onFavouriteBtnClicked = onFavouriteBtnClicked,
                getAllFavoriteSongs = getAllFavoriteSongs
            )
        }
        if (showMusicPlayer) {
            MusicPlayer(
                songName = playingSongName,
                songArtist = playingArtistName,
                onCloseClicked = {
                    onDestroyAudio()
                    showMusicPlayer = false
                },
                onPlayButtonClicked = { onPauseAudio() },
                isAudioPlaying = viewModel.isAudioPlaying
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(onNavigateBackClicked: () -> Unit) {
    DeezerTopAppBar(
        title = "Favorites",
        navigationIcon = DeezerIcons.ArrowBack,
        navigationContentDescription = null,
        onNavigateClick = onNavigateBackClicked
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
private fun FavoriteSongsList(
    modifier: Modifier,
    onSongClicked: (String, String, String) -> Unit,
    favoritesState: FavoritesState,
    onFavouriteBtnClicked: (Long) -> Unit,
    getAllFavoriteSongs: () -> Unit
) {
    when (favoritesState) {
        is FavoritesState.Nothing -> {}
        is FavoritesState.Loading -> {
            FullScreenProgIndicator()
        }

        is FavoritesState.Success -> {
            if (favoritesState.data.isNotEmpty()) {
                LazyColumn(
                    modifier = modifier,
                    contentPadding = PaddingValues(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(favoritesState.data, key = { it.id }) {
                        SongCard(
                            modifier = Modifier,
                            songImageUrl = it.songImgUrl,
                            songName = it.songName,
                            duration = "${it.duration.toDouble().seconds}",
                            favoriteIconInitVal = true,
                            onSongClicked = {
                                onSongClicked(it.audioUrl, it.artistName, it.songName)
                            },
                            onFavouriteBtnClicked = { onFavouriteBtnClicked(it.id) },
                            showAlbum = true,
                            albumName = it.albumName,
                            deleteAnimation = true,
                            sourceOfData = getAllFavoriteSongs
                        )
                    }
                }
            } else {
                EmptyListBox(
                    modifier = modifier.fillMaxSize(),
                    message = "You don't have any favorite song."
                )
            }
        }

        is FavoritesState.Error -> {

        }
    }
}

@Composable
private fun Title() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        text = "My Favorite Songs",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun ShowDeleteMessage(
    deleteState: DeleteState,
    viewModel: FavoritesViewModel
) {
    when (deleteState) {
        is DeleteState.Nothing -> {}
        is DeleteState.Success -> {
            Toast.makeText(
                LocalContext.current,
                deleteState.message,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetDeleteState()
        }

        is DeleteState.Error -> {
            Toast.makeText(
                LocalContext.current,
                deleteState.error,
                Toast.LENGTH_SHORT
            ).show()
            viewModel.resetDeleteState()
        }
    }
}