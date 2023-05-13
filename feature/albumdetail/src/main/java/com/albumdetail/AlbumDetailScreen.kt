package com.albumdetail

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.model.FavoriteSongs
import com.model.albumdetail.AlbumSong
import com.ui.FullScreenProgIndicator
import com.ui.MusicPlayer
import com.ui.PlayerHeight
import com.ui.SongCard
import kotlin.time.Duration.Companion.seconds

private val ALBUM_IMG_SIZE = 224.dp

@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    onNavigateBackClicked: () -> Unit
) {
    val viewModel: AlbumDetailViewModel = hiltViewModel()

    val albumDetailsState by viewModel.albumDetailsState.collectAsState()

    if (!viewModel.databaseStatus) {
        showMessage(
            context = LocalContext.current,
            message = "Favorite songs could not be found. Please try again later."
        )
    }

    AlbumDetailScreenContent(
        modifier = modifier,
        albumDetailsState = albumDetailsState,
        albumName = viewModel.albumName,
        onBackNavigateClicked = onNavigateBackClicked,
        viewModel = viewModel,
        isSongAvailableInFavorites = { viewModel.isSongAvailableInFavorites(it) },
        resetDatabaseState = viewModel::resetDatabaseState,
        addFavoriteSong = { viewModel.addFavoriteSong(it) },
        removeFavoriteSong = { viewModel.removeFavoriteSong(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumDetailScreenContent(
    modifier: Modifier,
    albumDetailsState: AlbumDetailsState,
    albumName: String,
    onBackNavigateClicked: () -> Unit,
    isSongAvailableInFavorites: (Int) -> Boolean,
    resetDatabaseState: () -> Unit,
    viewModel: AlbumDetailViewModel,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Int) -> Unit
) {
    Scaffold(
        topBar = {
            DeezerTopAppBar(
                title = albumName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = onBackNavigateClicked
            )
        }
    ) {
        when (albumDetailsState) {
            is AlbumDetailsState.Loading -> {
                FullScreenProgIndicator()
            }

            is AlbumDetailsState.Success -> {
                SuccessContent(
                    modifier,
                    it,
                    albumDetailsState,
                    viewModel,
                    isSongAvailableInFavorites,
                    resetDatabaseState,
                    addFavoriteSong,
                    removeFavoriteSong
                )
            }

            is AlbumDetailsState.Error -> {

            }
        }
    }
}

@Composable
private fun SuccessContent(
    modifier: Modifier,
    it: PaddingValues,
    albumDetailsState: AlbumDetailsState.Success,
    viewModel: AlbumDetailViewModel,
    isSongAvailableInFavorites: (Int) -> Boolean,
    resetDatabaseState: () -> Unit,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Int) -> Unit
) {
    var showMusicPlayer by rememberSaveable { mutableStateOf(false) }

    var playingSongName by remember { mutableStateOf("") }
    var playingSongArtist by remember { mutableStateOf("") }

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
        Title()
        SongList(
            modifier = modifier
                .weight(3f)
                .fillMaxSize()
                .padding(bottom = if (showMusicPlayer) PlayerHeight else 0.dp),
            songs = albumDetailsState.data.tracks.data,
            onSongClicked = { musicUrl, songName, songArtist ->
                playingSongName = songName
                playingSongArtist = songArtist

                if (!showMusicPlayer) {
                    showMusicPlayer = true
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
            onCloseClicked = { showMusicPlayer = false },
            onPlayButtonClicked = {}
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
private fun Title() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        text = "Songs",
        style = MaterialTheme.typography.titleLarge
    )
    Divider()
}

@Composable
private fun SongList(
    modifier: Modifier,
    songs: ArrayList<AlbumSong>,
    onSongClicked: (String, String, String) -> Unit,
    databaseState: DatabaseState,
    isSongAvailableInFavorites: (Int) -> Boolean,
    resetDatabaseState: () -> Unit,
    addFavoriteSong: (FavoriteSongs) -> Unit,
    removeFavoriteSong: (Int) -> Unit
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
                onSongClicked = {
                    onSongClicked(it.preview, it.title, it.artist.name)
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