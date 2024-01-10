package com.artistdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.models.ArtistAlbumsData
import com.ui.AlbumCard
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator

private val ARTIST_IMG_SIZE = 224.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    modifier: Modifier = Modifier,
    onArtistClick: (Long) -> Unit,
    upPress: () -> Unit
) {
    val viewModel: ArtistDetailViewModel = hiltViewModel()

    val uiState by viewModel.uiState.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(
            if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.6f)
            else Color.Black.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.background
        )
    )

    DeezerScaffold(
        modifier = modifier,
        topBar = {
            DeezerTopAppBar(
                title = uiState.artistName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                upPress = upPress
            )
        }
    ) { paddingValues ->
        ArtistDetailScreenContent(
            modifier = Modifier.padding(paddingValues),
            gradient = gradient,
            onAlbumClicked = onArtistClick,
            albumsState = uiState.albumsState,
            detailState = uiState.detailState
        )
    }
}

@Composable
private fun ArtistDetailScreenContent(
    modifier: Modifier,
    gradient: Brush,
    onAlbumClicked: (Long) -> Unit,
    detailState: DetailState,
    albumsState: AlbumsState
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ArtistImageSection(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .background(gradient),
            detailState = detailState
        )
        AlbumsSection(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize(),
            albumsState = albumsState,
            onAlbumClicked = onAlbumClicked
        )
    }
}

@Composable
private fun ArtistImageSection(
    modifier: Modifier,
    detailState: DetailState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (detailState) {
            is DetailState.Loading -> {
                DeezerCircularProgressIndicator()
            }

            is DetailState.Success -> {
                ArtistImage(artistImage = detailState.data.pictureBig)
            }

            is DetailState.Error -> {
                ErrorBox(
                    modifier = modifier,
                    errorMessage = detailState.message.asString()
                )
            }
        }
    }
}

@Composable
private fun ArtistImage(artistImage: String) {
    AnimatedImage(
        modifier = Modifier
            .size(ARTIST_IMG_SIZE)
            .clip(RoundedCornerShape(20)),
        imageUrl = artistImage
    )
}

@Composable
private fun AlbumsSection(
    modifier: Modifier,
    albumsState: AlbumsState,
    onAlbumClicked: (Long) -> Unit
) {
    Column(modifier = modifier) {
        when (albumsState) {
            is AlbumsState.Loading -> {
                FullScreenProgIndicator()
            }

            is AlbumsState.Success -> {
                AlbumsSectionTitle()
                AlbumsList(
                    artistAlbums = albumsState.albumsList.data,
                    onAlbumClicked = onAlbumClicked
                )
            }

            is AlbumsState.Error -> {
                ErrorBox(
                    modifier = modifier,
                    errorMessage = albumsState.message.asString()
                )
            }
        }
    }
}

@Composable
private fun AlbumsList(artistAlbums: ArrayList<ArtistAlbumsData>, onAlbumClicked: (Long) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(artistAlbums, key = { it.id }) { albums ->
            AlbumCard(
                albumImage = albums.coverBig,
                albumName = albums.title,
                albumId = albums.id,
                albumReleaseDate = albums.releaseDate,
                onAlbumClicked = onAlbumClicked
            )
        }
    }
}

@Composable
private fun AlbumsSectionTitle() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        text = "Albums",
        style = MaterialTheme.typography.titleLarge
    )
    Divider()
}