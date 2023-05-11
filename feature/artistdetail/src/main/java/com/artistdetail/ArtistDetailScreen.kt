package com.artistdetail

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerTopAppBar
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.GradientDeepPurple
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ArtistDetailScreen(modifier: Modifier = Modifier) {

    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setSystemBarsColor(color = Color.Black, darkIcons = false)
    }

    ArtistDetailScreenContent(modifier = modifier)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ArtistDetailScreenContent(modifier: Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            DeezerTopAppBar(
                title = "Zeynep Bastık",
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
            ArtistImageSection(modifier = modifier)
            AlbumsSection(modifier = modifier)
        }
    }
}

@Composable
private fun ArtistImageSection(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.Black)
            .padding(vertical = 32.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = modifier.size(256.dp),
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, GradientDeepPurple)
        ) {
            AnimatedImage(
                modifier = modifier.fillMaxSize(),
                imageUrl = "https://t4.ftcdn.net/jpg/04/23/60/03/240_F_423600360_ooiEiiUOaGpS5M0ZMRHurcaZ8VZ0Dbxr.jpg"
            )
        }
    }
}

@Composable
private fun AlbumsSection(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(10) {
            AlbumCard(modifier = modifier)
            if (it != 9) {
                Divider(modifier = modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp))
            }
        }
    }
}

@Composable
private fun AlbumCard(modifier: Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(96.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AlbumImage(
            modifier = modifier
                .weight(1f)
                .fillMaxSize(),
            albumUrl = "https://e-cdns-images.dzcdn.net/images/cover/ae7111ff020c7d7f7f64841a29dcb77c/500x500-000000-80-0-0.jpg"
        )
        AlbumName(
            modifier = modifier
                .weight(2f)
                .padding(horizontal = 32.dp),
            albumName = "Album Name"
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