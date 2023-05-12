package com.musicgenres

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.R
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.HeartRed
import com.model.Data
import com.ui.DeezerResourceCard

@Composable
fun MusicGenresScreen(
    modifier: Modifier = Modifier
) {
    val viewModel: MusicGenreViewModel = hiltViewModel()

    val musicGenresState by viewModel.musicGenresState.collectAsState()

    MusicCategoriesScreenContent(
        modifier = modifier,
        onNavigateArtistScreen = {},
        onGenreClicked = {},
        musicGenresState = musicGenresState
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicCategoriesScreenContent(
    modifier: Modifier,
    onNavigateArtistScreen: () -> Unit,
    onGenreClicked: (Int) -> Unit,
    musicGenresState: MusicGenresState
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DeezerTopAppBar(
                actionIcon = DeezerIcons.Favorite,
                actionIconTint = HeartRed,
                logoId = R.drawable.deezer_logo_light,
                logoContentDescription = null,
                actionContentDescription = null,
                onActionClick = onNavigateArtistScreen
            )
        }
    ) {
        when(musicGenresState) {
            is MusicGenresState.Loading -> {
                Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    DeezerCircularProgressIndicator()
                }
            }
            is MusicGenresState.Success -> {
                CategoryList(
                    modifier = modifier,
                    padding = it,
                    onGenreClicked = onGenreClicked,
                    musicGenres = musicGenresState.data
                )
            }
            is MusicGenresState.Error -> {
                Log.d("MUSIC GENRE ERROR", musicGenresState.message)
            }
        }
    }
}

@Composable
private fun CategoryList(
    modifier: Modifier,
    padding: PaddingValues,
    onGenreClicked: (Int) -> Unit,
    musicGenres: ArrayList<Data>
) {
    LazyVerticalGrid(
        modifier = modifier
            .fillMaxSize()
            .padding(padding),
        columns = GridCells.Fixed(count = 2),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(musicGenres, key = { it.id }) {
            DeezerResourceCard(
                modifier = modifier,
                onClick = { onGenreClicked(it.id) },
                resourceImgUrl = it.pictureMedium,
                resourceName = it.name
            )
        }
    }
}