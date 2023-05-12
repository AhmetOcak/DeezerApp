package com.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.designsystem.components.AnimatedImage

private val ALBUM_IMG_HEIGHT = 128.dp

@Composable
fun AlbumCard(
    modifier: Modifier = Modifier,
    albumImage: String,
    albumName: String,
    albumId: Int,
    onAlbumClicked: (Int) -> Unit,
    albumHeight: Dp = ALBUM_IMG_HEIGHT
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(albumHeight)
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
        style = MaterialTheme.typography.displayLarge
    )
}