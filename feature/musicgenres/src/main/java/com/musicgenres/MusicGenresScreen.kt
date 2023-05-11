package com.musicgenres

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.HeartRed
import com.ui.DeezerResourceCard

@Composable
fun MusicGenresScreen(
    modifier: Modifier = Modifier
) {

    MusicCategoriesScreenContent(
        modifier = modifier,
        onNavigateArtistScreen = {},
        onGenreClicked = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicCategoriesScreenContent(
    modifier: Modifier,
    onNavigateArtistScreen: () -> Unit,
    onGenreClicked: (Int) -> Unit
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DeezerTopAppBar(
                actionIcon = DeezerIcons.Favorite,
                actionIconTint = HeartRed,
                logoId = R.drawable.deezer_logo,
                logoContentDescription = null,
                actionContentDescription = null,
                onActionClick = onNavigateArtistScreen
            )
        }
    ) {
        CategoryList(
            modifier = modifier,
            padding = it,
            onGenreClicked = onGenreClicked
        )
    }
}

@Composable
private fun CategoryList(
    modifier: Modifier,
    padding: PaddingValues,
    onGenreClicked: (Int) -> Unit
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
        items(10) {
            DeezerResourceCard(
                modifier = modifier,
                onClick = { onGenreClicked(0) },
                resourceImgUrl = "https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/9f3f9bd9-0673-4276-bb34-71ece2a5820e/dfmgvtx-8b73761b-0527-4d12-b86c-92a04631a8c9.png/v1/fill/w_1920,h_1920,q_80,strp/darth_vader__ai_art__by_3d1viner_dfmgvtx-fullview.jpg?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOjdlMGQxODg5ODIyNjQzNzNhNWYwZDQxNWVhMGQyNmUwIiwiaXNzIjoidXJuOmFwcDo3ZTBkMTg4OTgyMjY0MzczYTVmMGQ0MTVlYTBkMjZlMCIsIm9iaiI6W1t7ImhlaWdodCI6Ijw9MTkyMCIsInBhdGgiOiJcL2ZcLzlmM2Y5YmQ5LTA2NzMtNDI3Ni1iYjM0LTcxZWNlMmE1ODIwZVwvZGZtZ3Z0eC04YjczNzYxYi0wNTI3LTRkMTItYjg2Yy05MmEwNDYzMWE4YzkucG5nIiwid2lkdGgiOiI8PTE5MjAifV1dLCJhdWQiOlsidXJuOnNlcnZpY2U6aW1hZ2Uub3BlcmF0aW9ucyJdfQ.HpHZ9AQtqpRbJO3cAL-BUBWNaN4OhElCGiaH7QKJo8M",
                resourceName = "Pop"
            )
        }
    }
}