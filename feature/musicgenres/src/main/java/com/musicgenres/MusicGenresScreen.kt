package com.musicgenres

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.R
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.components.DeezerScaffold
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.HeartRed
import com.models.Data
import com.ui.DeezerResourceCard
import com.ui.ErrorBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicGenresScreen(
    modifier: Modifier = Modifier,
    onMusicGenreClick: (Long, String) -> Unit,
    onFavoritesClick: () -> Unit,
    viewModel: MusicGenreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    DeezerScaffold(
        modifier = modifier,
        topBar = {
            DeezerTopAppBar(
                actionIcon = DeezerIcons.Favorite,
                actionIconTint = HeartRed,
                logoId = if (isSystemInDarkTheme()) R.drawable.deezer_logo_dark else R.drawable.deezer_logo_light,
                logoContentDescription = null,
                actionContentDescription = null,
                onActionClick = onFavoritesClick
            )
        }
    ) { paddingValues ->
        when (val state = uiState) {
            is MusicGenresUiState.Loading -> {
                Box(modifier = modifier.fillMaxSize().padding(paddingValues), contentAlignment = Alignment.Center) {
                    DeezerCircularProgressIndicator()
                }
            }
            is MusicGenresUiState.Success -> {
                CategoryList(
                    modifier = modifier.padding(paddingValues),
                    onGenreClicked = onMusicGenreClick,
                    musicGenres = state.musicGenresList
                )
            }
            is MusicGenresUiState.Error -> {
                ErrorBox(
                    modifier = modifier.fillMaxSize().padding(paddingValues),
                    errorMessage = state.message.asString()
                )
            }
        }
    }
}

@Composable
private fun CategoryList(
    modifier: Modifier,
    onGenreClicked: (Long, String) -> Unit,
    musicGenres: List<Data>
) {
    LazyVerticalGrid(
        modifier = modifier.fillMaxSize(),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(musicGenres, key = { it.id }) {
            DeezerResourceCard(
                onClick = remember { { onGenreClicked(it.id, it.name) } },
                resourceImgUrl = it.pictureMedium,
                resourceName = it.name
            )
        }
    }
}