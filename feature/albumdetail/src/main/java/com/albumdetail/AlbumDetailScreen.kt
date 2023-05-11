package com.albumdetail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.TransparentWhite
import com.ui.SongCard

@Composable
fun AlbumDetailScreen(modifier: Modifier = Modifier) {

    AlbumDetailScreenContent(modifier = modifier)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AlbumDetailScreenContent(modifier: Modifier) {
    Scaffold(
        topBar = {
            DeezerTopAppBar(
                title = "Album Name",
                navigationIcon = DeezerIcons.ArrowBack,
                navigationContentDescription = null,
                onNavigateClick = {},
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = TransparentWhite)
            )
        }
    ) {
        AlbumImage(
            modifier = modifier,
            albumImageUrl = "https://e-cdns-images.dzcdn.net/images/cover/b4985ab29adec28029d8ae8d6b143eed/500x500-000000-80-0-0.jpg"
        )
        SongList(modifier = modifier)
    }
}

@Composable
private fun SongList(modifier: Modifier) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 256.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            SongCard(
                modifier = modifier,
                songImageUrl = "https://e-cdns-images.dzcdn.net/images/cover/b4985ab29adec28029d8ae8d6b143eed/500x500-000000-80-0-0.jpg",
                songName = "Duman II",
                duration = "2:34",
                onSongClicked = {},
                onFavouriteBtnClicked = {},
                favoriteIconInitVal = false
            )
        }
    }
}

@Composable
private fun AlbumImage(modifier: Modifier, albumImageUrl: String) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(256.dp)
    ) {
        AnimatedImage(
            modifier = modifier.fillMaxSize(),
            imageUrl = albumImageUrl
        )
    }
}