package com.favorites

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
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
import com.ui.DeezerSubTitle
import com.ui.EmptyListBox
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator
import com.ui.MusicPlayer
import com.ui.PlayerHeight
import com.ui.SongCard
import kotlin.time.Duration.Companion.seconds

private val HEART_SIZE = 196.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(modifier: Modifier = Modifier, upPress: () -> Unit) {

    val viewModel: FavoritesViewModel = hiltViewModel()

    val deleteState by viewModel.deleteState.collectAsState()

    ShowDeleteMessage(deleteState, viewModel)

    Scaffold(
        modifier = modifier,
        topBar = {
            DeezerTopAppBar(
                title = "Favorites",
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                upPress = upPress
            )
        }
    ) { paddingValues ->
        FavoritesScreenContent(
            modifier = Modifier.padding(paddingValues),
            viewModel = viewModel,
            onFavouriteBtnClicked = remember(viewModel) { viewModel::removeFavoriteSong },
            getAllFavoriteSongs = remember(viewModel) { viewModel::getAllFavoriteSongs },
            onPlayAudio = remember(viewModel) { viewModel::playAudio },
            onPauseAudio = remember(viewModel) { viewModel::pauseAudio },
            onDestroyAudio = remember(viewModel) { viewModel::closeMediaPlayer }
        )
    }
}


@Composable
private fun FavoritesScreenContent(
    modifier: Modifier,
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

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LikesBoard(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        )
        DeezerSubTitle(
            isAudioPlaying = viewModel.isAudioPlaying,
            title = "My Favorite Songs"
        )
        FavoriteSongsList(
            modifier = Modifier
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
            onCloseClicked = remember { {
                    onDestroyAudio()
                    showMusicPlayer = false
                } },
            onPlayButtonClicked = remember { { onPauseAudio() } },
            isAudioPlaying = viewModel.isAudioPlaying
        )
    }
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
            ErrorBox(
                modifier = modifier,
                errorMessage = favoritesState.message
            )
        }
    }
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