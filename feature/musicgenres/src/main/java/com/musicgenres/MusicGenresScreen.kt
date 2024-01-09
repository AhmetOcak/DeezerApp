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
import androidx.compose.material3.Scaffold
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
    onFavoritesClick: () -> Unit
) {
    val viewModel: MusicGenreViewModel = hiltViewModel()

    val musicGenresState by viewModel.musicGenresState.collectAsState()

    Scaffold(
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
        MusicCategoriesScreenContent(
            modifier = Modifier.padding(paddingValues),
            onGenreClicked = onMusicGenreClick,
            musicGenresState = musicGenresState
        )
    }
}

@Composable
fun MusicCategoriesScreenContent(
    modifier: Modifier,
    onGenreClicked: (Long, String) -> Unit,
    musicGenresState: MusicGenresState,
) {
    when (musicGenresState) {
        is MusicGenresState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                DeezerCircularProgressIndicator()
            }
        }

        is MusicGenresState.Success -> {
            CategoryList(
                modifier = modifier,
                onGenreClicked = onGenreClicked,
                musicGenres = musicGenresState.data
            )
        }

        is MusicGenresState.Error -> {
            ErrorBox(
                modifier = modifier.fillMaxSize(),
                errorMessage = musicGenresState.message
            )
        }
    }
}

@Composable
private fun CategoryList(
    modifier: Modifier,
    onGenreClicked: (Long, String) -> Unit,
    musicGenres: ArrayList<Data>
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