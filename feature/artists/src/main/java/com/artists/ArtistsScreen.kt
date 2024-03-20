package com.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ahmetocak.models.ArtistData
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.ui.DeezerResourceCard
import com.ui.ErrorBox
import com.ui.FullScreenProgIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    modifier: Modifier = Modifier,
    onArtistClick: (Long) -> Unit,
    upPress: () -> Unit,
    genreName: String,
    viewModel: ArtistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DeezerScaffold(
        modifier = modifier,
        topBar = {
            DeezerTopAppBar(
                title = genreName,
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                upPress = upPress
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is ArtistUiState.Loading -> {
                FullScreenProgIndicator()
            }

            is ArtistUiState.Success -> {
                ArtistList(
                    modifier = modifier.padding(paddingValues),
                    artists = state.artistList,
                    onArtistClicked = onArtistClick
                )
            }

            is ArtistUiState.Error -> {
                ErrorBox(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    errorMessage = state.message.asString()
                )
            }
        }
    }
}

@Composable
private fun ArtistList(
    modifier: Modifier,
    artists: List<ArtistData>,
    onArtistClicked: (Long) -> Unit
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(artists, key = { it.id }) {
            DeezerResourceCard(
                onClick = remember { { onArtistClicked(it.id) } },
                resourceImgUrl = it.image,
                resourceName = it.name
            )
        }
    }
}