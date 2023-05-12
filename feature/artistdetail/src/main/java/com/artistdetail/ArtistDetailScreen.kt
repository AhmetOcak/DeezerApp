package com.artistdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.components.onLoadState
import com.designsystem.components.rememberLazyListState
import com.designsystem.icons.DeezerIcons
import com.model.TrackData
import com.ui.AlbumCard

private val ARTIST_IMG_SIZE = 224.dp

@Composable
fun ArtistDetailScreen(modifier: Modifier = Modifier) {

    val viewModel: ArtistDetailViewModel = hiltViewModel()

    viewModel.getArtistDetails(299117)

    val artistDetailState by viewModel.artistDetailState.collectAsState()

    val gradient = Brush.verticalGradient(
        colors = listOf(MaterialTheme.colorScheme.primary.copy(alpha = 0.7f), MaterialTheme.colorScheme.background)
    )

    ArtistDetailScreenContent(
        modifier = modifier,
        trackList = viewModel.trackList.collectAsLazyPagingItems(),
        artistDetailState = artistDetailState,
        artistName = viewModel.artistName,
        gradient = gradient
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistDetailScreenContent(
    modifier: Modifier,
    trackList: LazyPagingItems<TrackData>,
    artistDetailState: ArtistDetailState,
    artistName: String,
    gradient: Brush
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopBar(artistName) }
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
                artistDetailState = artistDetailState
            )
            AlbumsSection(
                modifier = modifier
                    .weight(3f)
                    .fillMaxSize(),
                trackList = trackList
            )
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun TopBar(artistName: String) {
    DeezerTopAppBar(
        title = artistName,
        navigationIcon = DeezerIcons.ArrowBack,
        navigationContentDescription = null,
        onNavigateClick = {}
    )
}

/*
Todo: Error eklenecek
 */
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

/*
Todo: trackList'ten albumler ayıklaancak
 */
@Composable
private fun AlbumsSection(modifier: Modifier, trackList: LazyPagingItems<TrackData>) {
    Column(modifier = modifier) {
        AlbumsSectionTitle()
        AlbumsList(trackList = trackList)
    }
}

@Composable
private fun AlbumsList(trackList: LazyPagingItems<TrackData>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        state = trackList.rememberLazyListState()
    ) {
        items(trackList, key = { it.id }) {
            AlbumCard(
                albumImage = it?.album?.coverBig ?: "",
                albumName = it?.album?.title ?: "",
                albumId = it?.album?.id ?: 0,
                onAlbumClicked = {}
            )
        }

        onLoadState(
            modifier = Modifier,
            loadState = trackList.loadState.append
        )

        onLoadState(
            modifier = Modifier,
            loadState = trackList.loadState.refresh
        )
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