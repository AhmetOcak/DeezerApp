package com.favorites

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.HeartRed
import com.designsystem.utils.Music
import com.models.FavoriteSongs
import com.ui.EmptyListBox
import com.ui.SongCard
import kotlin.time.Duration.Companion.seconds

private val HEART_SIZE = 196.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel(),
    onSongClicked: (Music) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.userMessages.isNotEmpty()) {
        Toast.makeText(
            LocalContext.current,
            uiState.userMessages.first().asString(),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.consumedUserMessages()
    }

    DeezerScaffold(
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
            onFavouriteBtnClicked = remember(viewModel) { viewModel::removeFavoriteSong },
            getAllFavoriteSongs = remember(viewModel) { viewModel::getAllFavoriteSongs },
            favoriteSongsList = uiState.favoriteSongsList,
            onSongClicked = onSongClicked
        )
    }
}


@Composable
private fun FavoritesScreenContent(
    modifier: Modifier,
    onFavouriteBtnClicked: (Long) -> Unit,
    getAllFavoriteSongs: () -> Unit,
    favoriteSongsList: List<FavoriteSongs>,
    onSongClicked: (Music) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        LikesBoard(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp),
            text = "My Favorite Songs",
            style = MaterialTheme.typography.titleLarge
        )
        FavoriteSongsList(
            modifier = Modifier
                .weight(4f)
                .fillMaxSize(),
            onSongClicked = onSongClicked,
            favoriteSongsList = favoriteSongsList,
            onFavouriteBtnClicked = onFavouriteBtnClicked,
            getAllFavoriteSongs = getAllFavoriteSongs
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
    onSongClicked: (Music) -> Unit,
    favoriteSongsList: List<FavoriteSongs>,
    onFavouriteBtnClicked: (Long) -> Unit,
    getAllFavoriteSongs: () -> Unit,
) {
    if (favoriteSongsList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(favoriteSongsList, key = { it.id }) {
                SongCard(
                    songImageUrl = it.songImgUrl,
                    songName = it.songName,
                    duration = "${it.duration.toDouble().seconds}",
                    favoriteIconInitVal = true,
                    onSongClicked = remember {
                        {
                            onSongClicked(
                                Music(
                                    imageUrl = it.songImgUrl,
                                    name = it.songName,
                                    artistName = it.artistName,
                                    audioUrl = it.audioUrl
                                )
                            )
                        }
                    },
                    onFavouriteBtnClicked = remember {
                        { onFavouriteBtnClicked(it.id) }
                    },
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