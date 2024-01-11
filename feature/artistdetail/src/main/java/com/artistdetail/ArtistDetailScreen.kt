package com.artistdetail

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.designsystem.utils.UiText
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.models.ArtistAlbums
import com.ui.AlbumCard
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator

private val ARTIST_IMG_SIZE = 224.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistDetailScreen(
    modifier: Modifier = Modifier,
    onArtistClick: (Long) -> Unit,
    upPress: () -> Unit,
    viewModel: ArtistDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

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
            onAlbumClicked = onArtistClick,
            detailState = uiState.detailState,
            albums = uiState.albumsList?.collectAsLazyPagingItems(),
            onPainterStateSuccess = remember(viewModel) {
                { viewModel.createPalette(it.toBitmap()) }
            },
            gradientColorList = uiState.imageColors
        )
    }
}

@Composable
private fun ArtistDetailScreenContent(
    modifier: Modifier,
    gradientColorList: List<Color>,
    onAlbumClicked: (Long) -> Unit,
    detailState: DetailState,
    albums: LazyPagingItems<ArtistAlbums>?,
    onPainterStateSuccess: (Drawable) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        ArtistImageSection(
            modifier = Modifier
                .weight(2f)
                .fillMaxSize()
                .background(Brush.linearGradient(gradientColorList)),
            detailState = detailState,
            onPainterStateSuccess = onPainterStateSuccess
        )
        AlbumsSection(
            modifier = Modifier
                .weight(3f)
                .fillMaxSize(),
            onAlbumClicked = onAlbumClicked,
            albums = albums
        )
    }
}

@Composable
private fun ArtistImageSection(
    modifier: Modifier,
    detailState: DetailState,
    onPainterStateSuccess: (Drawable) -> Unit
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
                AnimatedImage(
                    modifier = Modifier
                        .size(ARTIST_IMG_SIZE)
                        .clip(RoundedCornerShape(20)),
                    imageUrl = detailState.data.pictureBig,
                    onPainterStateSuccess = onPainterStateSuccess
                )
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
private fun AlbumsSection(
    modifier: Modifier,
    onAlbumClicked: (Long) -> Unit,
    albums: LazyPagingItems<ArtistAlbums>?
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(vertical = 16.dp),
            text = "Albums",
            style = MaterialTheme.typography.titleLarge
        )
        Divider()
        if (albums != null) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                items(albums.itemCount, key = albums.itemKey { it.id }) { index ->
                    albums[index]?.let { album ->
                        AlbumCard(
                            albumImage = album.coverBig,
                            albumName = album.title,
                            albumId = album.id,
                            albumReleaseDate = album.releaseDate,
                            onAlbumClicked = onAlbumClicked
                        )
                    }
                }

                onLoadStateRefresh(albums.loadState.refresh)
                onLoadStateAppend(albums.loadState.append)
            }
        }
    }
}

private fun LazyListScope.onLoadStateRefresh(loadState: LoadState) {
    when (loadState) {
        is LoadState.Error -> {
            item {
                ErrorBox(
                    errorMessage = loadState.error.message
                        ?: UiText.DynamicString(
                            "Something went wrong. Please try again later!!"
                        ).asString()
                )
            }
        }

        is LoadState.Loading -> {
            item {
                FullScreenProgIndicator()
            }
        }

        else -> {}
    }
}

private fun LazyListScope.onLoadStateAppend(loadState: LoadState) {
    when (loadState) {
        is LoadState.Error -> {
            item {
                ErrorBox(
                    errorMessage = loadState.error.message
                        ?: UiText.DynamicString(
                            "Something went wrong. Please try again later!!"
                        ).asString()
                )
            }
        }

        is LoadState.Loading -> {
            item {
                FullScreenProgIndicator()
            }
        }

        else -> {}
    }
}