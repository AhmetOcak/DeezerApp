package com.albumdetail

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.models.FavoriteSongs
import com.models.albumdetail.AlbumSong
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
    upPress: () -> Unit,
    viewModel: AlbumDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (!uiState.isDatabaseAvailable) {
        showMessage(
            context = LocalContext.current,
            message = "Favorite songs could not be found. Please try again later."
        )
    }

    if (uiState.errorMessages.isNotEmpty()) {
        showMessage(
            context = LocalContext.current,
            message = uiState.errorMessages.first().asString()
        )
        viewModel.consumedErrorMessages()
    }

    if (uiState.userMessages.isNotEmpty()) {
        showMessage(
            context = LocalContext.current,
            message = uiState.userMessages.first().asString()
        )
        viewModel.consumedUserMessages()
    }

    DeezerScaffold(
        modifier = modifier,
        topBar = {
            DeezerTopAppBar(
                title = uiState.albumName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                upPress = upPress
            )
        }
    ) { paddingValues ->
        when (val state = uiState.detailState) {
            is DetailsState.Loading -> {
                FullScreenProgIndicator()
            }

            is DetailsState.Success -> {
                AlbumDetailScreenContent(
                    modifier = Modifier.padding(paddingValues),
                    isSongAvailableInFavorites = remember(viewModel) { viewModel::isSongAvailableInFavorites },
                    addFavoriteSong = remember(viewModel) { viewModel::addFavoriteSong },
                    removeFavoriteSong = remember(viewModel) { viewModel::removeFavoriteSong },
                    onPlayAudio = remember(viewModel) { viewModel::playAudio },
                    onPauseAudio = remember(viewModel) { viewModel::pauseAudio },
                    onDestroyAudio = remember(viewModel) { viewModel::closeMediaPlayer },
                    isAudioPlaying = uiState.isAudioPlaying,
                    albumImgUrl = state.data.coverBig,
                    tracks = state.data.tracks.data,
                    gradientColorList = uiState.imageColor,
                    onPainterStateSuccess = remember(viewModel) {
                        { viewModel.createPalette(it.toBitmap()) }
                    }
                )
            }

            is DetailsState.Error -> {
                ErrorBox(
                    modifier = modifier,
                    errorMessage = state.message.asString()
                )
            }
        }
    }
}

@Composable
private fun AlbumDetailScreenContent(
    modifier: Modifier,
    isSongAvailableInFavorites: (Long) -> Boolean,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Long) -> Unit,
    onPlayAudio: (String) -> Unit,
    onPauseAudio: () -> Unit,
    onDestroyAudio: () -> Unit,
    isAudioPlaying: Boolean,
    albumImgUrl: String,
    tracks: List<AlbumSong>,
    gradientColorList: List<Color>,
    onPainterStateSuccess: (Drawable) -> Unit
) {
    var showMusicPlayer by rememberSaveable { mutableStateOf(false) }

    var playingSongName by remember { mutableStateOf("") }
    var playingSongArtist by remember { mutableStateOf("") }

    Column(modifier = modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .background(Brush.linearGradient(gradientColorList)),
            contentAlignment = Alignment.Center
        ) {
            AnimatedImage(
                modifier = Modifier
                    .size(ALBUM_IMG_SIZE)
                    .clip(RoundedCornerShape(20)),
                imageUrl = albumImgUrl,
                onPainterStateSuccess = onPainterStateSuccess
            )
        }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(vertical = 16.dp),
            text = "Songs",
            style = MaterialTheme.typography.titleLarge
        )
        SongList(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize()
                .padding(bottom = if (showMusicPlayer) PlayerHeight else 0.dp),
            songs = tracks,
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
            isSongAvailableInFavorites = isSongAvailableInFavorites,
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
            isAudioPlaying = isAudioPlaying
        )
    }
}

@Composable
private fun SongList(
    modifier: Modifier,
    songs: List<AlbumSong>,
    onSongClicked: (String, String, String) -> Unit,
    isSongAvailableInFavorites: (Long) -> Boolean,
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
}

private fun showMessage(context: Context, message: String) {
    Toast.makeText(
        context,
        message,
        Toast.LENGTH_SHORT
    ).show()
}