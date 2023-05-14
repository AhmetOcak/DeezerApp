package com.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
    albumId: Long,
    albumReleaseDate: String,
    onAlbumClicked: (Long) -> Unit,
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
        AlbumDetails(
            modifier = modifier
                .weight(2f)
                .padding(horizontal = 32.dp),
            albumName = albumName,
            date = albumReleaseDate
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
private fun AlbumDetails(modifier: Modifier, albumName: String, date: String) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        AlbumName(albumName = albumName)
        AlbumReleaseDate(date = date)
    }
}

@Composable
private fun AlbumName(albumName: String) {
    Text(
        text = albumName,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
private fun AlbumReleaseDate(date: String) {
    Text(
        text = date.replace('-', '.'),
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.displayMedium
    )
}