package com.artists

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.ui.DeezerResourceCard

@Composable
fun ArtistsScreen(modifier: Modifier = Modifier) {

    ArtistsScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistsScreenContent(modifier: Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DeezerTopAppBar(
                title = "Category Name",
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = {}
            )
        },
    ) {
        ArtistList(modifier = modifier, scaffoldPadding = it)
    }
}

@Composable
private fun ArtistList(
    modifier: Modifier,
    scaffoldPadding: PaddingValues
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
        items(10) {
            DeezerResourceCard(
                modifier = modifier,
                onClick = { /*TODO*/ },
                resourceImgUrl = "https://e-cdns-images.dzcdn.net/images/artist/50c9aca4265d49bc492fb29d2b824aea/500x500-000000-80-0-0.jpg",
                resourceName = "Duman"
            )
        }
    }
}