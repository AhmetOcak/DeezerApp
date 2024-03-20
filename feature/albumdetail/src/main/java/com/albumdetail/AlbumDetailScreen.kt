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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.models.albumdetail.AlbumSong
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.utils.Music
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator
import com.ui.SongCard
import kotlin.time.Duration.Companion.seconds

private val ALBUM_IMG_SIZE = 224.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlbumDetailScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    viewModel: AlbumDetailViewModel = hiltViewModel(),
    onSongClicked: (Music) -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    isSongAvailableInFavorites = remember { viewModel::isSongAvailableInFavorites },
                    addFavoriteSong = remember { viewModel::addFavoriteSong },
                    removeFavoriteSong = remember { viewModel::removeFavoriteSong },
                    albumImgUrl = state.data.coverBig,
                    tracks = state.data.tracks.data,
                    gradientColorList = uiState.imageColor,
                    onPainterStateSuccess = remember { { viewModel.createPalette(it.toBitmap()) } },
                    onSongClicked = onSongClicked
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
    albumImgUrl: String,
    tracks: List<AlbumSong>,
    gradientColorList: List<Color>,
    onPainterStateSuccess: (Drawable) -> Unit,
    onSongClicked: (Music) -> Unit
) {
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
                .fillMaxSize(),
            songs = tracks,
            onSongClicked = onSongClicked,
            isSongAvailableInFavorites = isSongAvailableInFavorites,
            addFavoriteSong = addFavoriteSong,
            removeFavoriteSong = removeFavoriteSong
        )
    }
}

@Composable
private fun SongList(
    modifier: Modifier,
    songs: List<AlbumSong>,
    onSongClicked: (Music) -> Unit,
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
                    {
                        onSongClicked(
                            Music(
                                imageUrl = it.album.coverBig,
                                name = it.title,
                                artistName = it.artist.name,
                                audioUrl = it.preview
                            )
                        )
                    }
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