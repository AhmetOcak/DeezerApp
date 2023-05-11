package com.artistdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
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
import com.designsystem.theme.GradientDeepPurple
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.model.TrackData

private val ARTIST_IMG_SIZE = 224.dp
private val ALBUM_IMG_HEIGHT = 128.dp

@Composable
fun ArtistDetailScreen(modifier: Modifier = Modifier) {

    val systemUiController = rememberSystemUiController()

    val viewModel: ArtistDetailViewModel = hiltViewModel()

    val artistDetailState by viewModel.artistDetailState.collectAsState()

    SideEffect {
        systemUiController.setSystemBarsColor(color = Color.Black, darkIcons = false)
    }

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
                onNavigateClick = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Black)
            )
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            ArtistImageSection(
                modifier = modifier.weight(2f),
                artistDetailState = artistDetailState
            )
            AlbumsSection(modifier = modifier.weight(3f), trackList = trackList)
        }
    }
}

/*
Todo: Error eklenecek
 */
@Composable
private fun ArtistImageSection(modifier: Modifier, artistDetailState: ArtistDetailState) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Black),
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
    Card(
        modifier = Modifier.size(ARTIST_IMG_SIZE),
        border = BorderStroke(2.dp, GradientDeepPurple),
        shape = CircleShape,
        colors = CardDefaults.cardColors(containerColor = Color.Black)
    ) {
        AnimatedImage(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape),
            imageUrl = artistImage,
            contentScale = ContentScale.Crop
        )
    }
}

/*
Todo: trackList'ten albumler ayıklaancak
 */
@Composable
private fun AlbumsSection(modifier: Modifier, trackList: LazyPagingItems<TrackData>) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
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

@Composable
private fun AlbumCard(
    modifier: Modifier,
    albumImage: String,
    albumName: String,
    albumId: Int,
    onAlbumClicked: (Int) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(ALBUM_IMG_HEIGHT)
            .clickable(onClick = { onAlbumClicked(albumId) })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AlbumImage(
            modifier = modifier
                .weight(1f)
                .fillMaxSize(),
            albumUrl = albumImage
        )
        AlbumName(
            modifier = modifier
                .weight(2f)
                .padding(horizontal = 32.dp),
            albumName = albumName
        )
    }
}

@Composable
private fun AlbumImage(modifier: Modifier, albumUrl: String) {
    Card(modifier = modifier, shape = RoundedCornerShape(10)) {
        AnimatedImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = albumUrl
        )
    }
}

@Composable
private fun AlbumName(modifier: Modifier, albumName: String) {
    Text(
        modifier = modifier,
        text = albumName,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyMedium
    )
}