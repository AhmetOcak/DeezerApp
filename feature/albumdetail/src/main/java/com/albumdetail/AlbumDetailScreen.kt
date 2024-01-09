package com.albumdetail

import android.content.Context
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.models.FavoriteSongs
import com.models.albumdetail.AlbumSong
import com.ui.DeezerSubTitle
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator
import com.ui.MusicPlayer
import com.ui.PlayerHeight
import com.ui.SongCard
import kotlin.time.Duration.Companion.seconds

private val ALBUM_IMG_SIZE = 224.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit
) {
    val viewModel: AlbumDetailViewModel = hiltViewModel()

    val albumDetailsState by viewModel.albumDetailsState.collectAsState()

    if (!viewModel.databaseStatus) {
        showMessage(
            context = LocalContext.current,
            message = "Favorite songs could not be found. Please try again later."
        )
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DeezerTopAppBar(
                title = viewModel.albumName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                upPress = upPress
            )
        }
    ) { paddingValues ->
        AlbumDetailScreenContent(
            modifier = Modifier.padding(paddingValues),
            albumDetailsState = albumDetailsState,
            viewModel = viewModel,
            isSongAvailableInFavorites = remember(viewModel) { viewModel::isSongAvailableInFavorites },
            resetDatabaseState = remember(viewModel)  { viewModel::resetDatabaseState },
            addFavoriteSong = remember(viewModel)  { viewModel::addFavoriteSong },
            removeFavoriteSong = remember(viewModel)  { viewModel::removeFavoriteSong },
            onPlayAudio = remember(viewModel)  { viewModel::playAudio },
            onPauseAudio = remember(viewModel)  { viewModel::pauseAudio },
            onDestroyAudio = remember(viewModel)  { viewModel::closeMediaPlayer }
        )
    }
}

@Composable
private fun AlbumDetailScreenContent(
    modifier: Modifier,
    albumDetailsState: AlbumDetailsState,
    isSongAvailableInFavorites: (Long) -> Boolean,
    resetDatabaseState: () -> Unit,
    viewModel: AlbumDetailViewModel,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Long) -> Unit,
    onPlayAudio: (String) -> Unit,
    onPauseAudio: () -> Unit,
    onDestroyAudio: () -> Unit
) {
    when (albumDetailsState) {
        is AlbumDetailsState.Loading -> {
            FullScreenProgIndicator()
        }

        is AlbumDetailsState.Success -> {
            SuccessContent(
                modifier = modifier,
                albumDetailsState = albumDetailsState,
                viewModel = viewModel,
                isSongAvailableInFavorites = isSongAvailableInFavorites,
                resetDatabaseState = resetDatabaseState,
                addFavoriteSong = addFavoriteSong,
                removeFavoriteSong = removeFavoriteSong,
                onPlayAudio = onPlayAudio,
                onPauseAudio = onPauseAudio,
                onDestroyAudio = onDestroyAudio
            )
        }

        is AlbumDetailsState.Error -> {
            ErrorBox(
                modifier = modifier,
                errorMessage = albumDetailsState.message
            )
        }
    }
}

@Composable
private fun SuccessContent(
    modifier: Modifier,
    albumDetailsState: AlbumDetailsState.Success,
    viewModel: AlbumDetailViewModel,
    isSongAvailableInFavorites: (Long) -> Boolean,
    resetDatabaseState: () -> Unit,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Long) -> Unit,
    onPlayAudio: (String) -> Unit,
    onPauseAudio: () -> Unit,
    onDestroyAudio: () -> Unit
) {
    var showMusicPlayer by rememberSaveable { mutableStateOf(false) }

    var playingSongName by remember { mutableStateOf("") }
    var playingSongArtist by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        AlbumImage(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize(),
            albumImageUrl = albumDetailsState.data.coverBig
        )
        DeezerSubTitle(
            isAudioPlaying = viewModel.isAudioPlaying,
            title = "Songs"
        )
        SongList(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
                .padding(bottom = if (showMusicPlayer) PlayerHeight else 0.dp),
            songs = albumDetailsState.data.tracks.data,
            onSongClicked = remember {
                { musicUrl, songName, songArtist ->
                    onPlayAudio(musicUrl)

                    playingSongName = songName
                    playingSongArtist = songArtist

                    if (!showMusicPlayer) {
                        showMusicPlayer = true
                    }
                }
            },
            databaseState = viewModel.databaseState.collectAsState().value,
            isSongAvailableInFavorites = isSongAvailableInFavorites,
            resetDatabaseState = resetDatabaseState,
            addFavoriteSong = addFavoriteSong,
            removeFavoriteSong = removeFavoriteSong
        )
    }
    if (showMusicPlayer) {
        MusicPlayer(
            songName = playingSongName,
            songArtist = playingSongArtist,
            onCloseClicked = remember {
                {
                    onDestroyAudio()
                    showMusicPlayer = false
                }
            },
            onPlayButtonClicked = { onPauseAudio() },
            isAudioPlaying = viewModel.isAudioPlaying
        )
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
private fun SongList(
    modifier: Modifier,
    songs: ArrayList<AlbumSong>,
    onSongClicked: (String, String, String) -> Unit,
    databaseState: DatabaseState,
    isSongAvailableInFavorites: (Long) -> Boolean,
    resetDatabaseState: () -> Unit,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(songs, key = { it.id }) {
            SongCard(
                modifier = Modifier,
                songImageUrl = it.album.coverBig,
                songName = it.title,
                duration = "${it.duration.toDouble().seconds}",
                onSongClicked = remember {
                    { onSongClicked(it.preview, it.title, it.artist.name) }
                },
                onFavouriteBtnClicked = {
                    if (isSongAvailableInFavorites(it.id)) {
                        removeFavoriteSong(it.id)
                    } else {
                        addFavoriteSong(
                            FavoriteSongs(
                                id = it.id,
                                songImgUrl = it.album.coverBig,
                                songName = it.title,
                                duration = it.duration,
                                artistName = it.artist.name,
                                audioUrl = it.preview,
                                albumName = it.album.title
                            )
                        )
                    }
                },
                favoriteIconInitVal = isSongAvailableInFavorites(it.id)
            )
        }
    }
    when (databaseState) {
        is DatabaseState.Nothing -> {}
        is DatabaseState.Loading -> {}
        is DatabaseState.Success -> {
            showMessage(context = LocalContext.current, message = databaseState.message)
            resetDatabaseState()
        }

        is DatabaseState.Error -> {
            showMessage(context = LocalContext.current, message = databaseState.message)
            resetDatabaseState()
        }
    }
}

private fun showMessage(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}