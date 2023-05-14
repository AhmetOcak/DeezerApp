package com.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.model.ArtistData
import com.ui.DeezerResourceCard
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator

@Composable
fun ArtistsScreen(
    modifier: Modifier = Modifier,
    onNavigateArtistDetailScreen: (Long) -> Unit,
    onNavigateBackClicked: () -> Unit,
    genreName: String
) {
    val viewModel: ArtistViewModel = hiltViewModel()

    ArtistsScreenContent(
        modifier = modifier,
        artistState = viewModel.artistState.collectAsState().value,
        onArtistClicked = { onNavigateArtistDetailScreen(it) },
        onNavigateBackClicked = onNavigateBackClicked,
        genreName = genreName
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistsScreenContent(
    modifier: Modifier,
    artistState: ArtistState,
    onArtistClicked: (Long) -> Unit,
    onNavigateBackClicked: () -> Unit,
    genreName: String
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DeezerTopAppBar(
                title = genreName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = onNavigateBackClicked
            )
        },
    ) {
        when (artistState) {
            is ArtistState.Loading -> {
                FullScreenProgIndicator()
            }

            is ArtistState.Success -> {
                ArtistList(
                    modifier = modifier,
                    scaffoldPadding = it,
                    artists = artistState.data,
                    onArtistClicked = onArtistClicked
                )
            }

            is ArtistState.Error -> {
                ErrorBox(
                    modifier = modifier.fillMaxSize(),
                    errorMessage = artistState.message
                )
            }
        }
    }
}

@Composable
private fun ArtistList(
    modifier: Modifier,
    scaffoldPadding: PaddingValues,
    artists: ArrayList<ArtistData>,
    onArtistClicked: (Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(scaffoldPadding),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(artists, key = { it.id }) {
            DeezerResourceCard(
                modifier = modifier,
                onClick = { onArtistClicked(it.id) },
                resourceImgUrl = it.pictureMedium,
                resourceName = it.name
            )
        }
    }
}