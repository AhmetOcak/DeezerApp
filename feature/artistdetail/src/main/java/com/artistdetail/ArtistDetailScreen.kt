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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.model.ArtistAlbumsData
import com.ui.AlbumCard
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator

private val ARTIST_IMG_SIZE = 224.dp

@Composable
fun ArtistDetailScreen(
    modifier: Modifier = Modifier,
    onNavigateAlbumDetails: (Long) -> Unit,
    onNavigateBackClicked: () -> Unit
) {
    val viewModel: ArtistDetailViewModel = hiltViewModel()

    val gradient = Brush.verticalGradient(
        colors = listOf(
            if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.6f)
            else Color.Black.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.background
        )
    )

    ArtistDetailScreenContent(
        modifier = modifier,
        viewModel = viewModel,
        gradient = gradient,
        onNavigateBackClicked = onNavigateBackClicked,
        onAlbumClicked = { onNavigateAlbumDetails(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistDetailScreenContent(
    modifier: Modifier,
    viewModel: ArtistDetailViewModel,
    gradient: Brush,
    onNavigateBackClicked: () -> Unit,
    onAlbumClicked: (Long) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopBar(viewModel.artistName, onNavigateBackClicked) }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ArtistImageSection(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize()
                    .background(gradient),
                artistDetailState = viewModel.artistDetailState.collectAsState().value
            )
            AlbumsSection(
                modifier = modifier
                    .weight(3f)
                    .fillMaxSize(),
                artistAlbumsState = viewModel.artistAlbumsState.collectAsState().value,
                onAlbumClicked = onAlbumClicked
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(artistName: String, onNavigateBackClicked: () -> Unit) {
    DeezerTopAppBar(
        title = artistName,
        navigationIcon = DeezerIcons.ArrowBack,
        navigationContentDescription = null,
        onNavigateClick = onNavigateBackClicked
    )
}

@Composable
private fun ArtistImageSection(
    modifier: Modifier,
    artistDetailState: ArtistDetailState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        when (artistDetailState) {
            is ArtistDetailState.Loading -> {
                DeezerCircularProgressIndicator()
            }

            is ArtistDetailState.Success -> {
                ArtistImage(artistImage = artistDetailState.data.pictureBig)
            }

            is ArtistDetailState.Error -> {
                ErrorBox(
                    modifier = modifier,
                    errorMessage = artistDetailState.message
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
    artistAlbumsState: ArtistAlbumsState,
    onAlbumClicked: (Long) -> Unit
) {
    Column(modifier = modifier) {
        when (artistAlbumsState) {
            is ArtistAlbumsState.Loading -> {
                FullScreenProgIndicator()
            }

            is ArtistAlbumsState.Success -> {
                AlbumsSectionTitle()
                AlbumsList(
                    artistAlbums = artistAlbumsState.data.data,
                    onAlbumClicked = onAlbumClicked
                )
            }

            is ArtistAlbumsState.Error -> {
                ErrorBox(
                    modifier = modifier,
                    errorMessage = artistAlbumsState.message
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
                onAlbumClicked = {
                    onAlbumClicked(albums.id)
                }
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