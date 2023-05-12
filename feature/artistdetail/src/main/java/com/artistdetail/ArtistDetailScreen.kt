package com.artistdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
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

    val artistDetailState by viewModel.artistDetailState.collectAsState()

    ArtistDetailScreenContent(
        modifier = modifier,
        trackList = viewModel.trackList.collectAsLazyPagingItems(),
        artistDetailState = artistDetailState,
        artistName = viewModel.artistName
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistDetailScreenContent(
    modifier: Modifier,
    trackList: LazyPagingItems<TrackData>,
    artistDetailState: ArtistDetailState,
    artistName: String
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DeezerTopAppBar(
                title = artistName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = {}
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ArtistImageSection(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize(),
                artistDetailState = artistDetailState
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
            AlbumsSection(modifier = modifier
                .weight(3f)
                .fillMaxSize(), trackList = trackList)
        }
    }
}

/*
Todo: Error eklenecek
 */
@Composable
private fun ArtistImageSection(modifier: Modifier, artistDetailState: ArtistDetailState) {
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
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 16.dp),
        state = trackList.rememberLazyListState()
    ) {
        items(trackList, key = { it.id }) {
            AlbumCard(
                modifier = Modifier,
                albumImage = it?.album?.coverBig ?: "",
                albumName = it?.album?.title ?: "",
                albumId = it?.album?.id ?: 0,
                onAlbumClicked = {}
            )
            Divider(modifier = Modifier.padding(horizontal = 16.dp))
        }

        onLoadState(
            modifier = modifier,
            loadState = trackList.loadState.append
        )

        onLoadState(
            modifier = modifier,
            loadState = trackList.loadState.refresh
        )
    }
}