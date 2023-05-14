package com.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@Composable
fun DeezerSubTitle(
    modifier: Modifier = Modifier,
    isAudioPlaying: Boolean,
    title: String
) {
    Title(title = title)
    Row(modifier.height(GIF_HEIGHT)) {
        if (isAudioPlaying) {
            Gif(context = LocalContext.current)
        }
    }
    Divider()
}

@Composable
private fun Title(title: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        text = title,
        style = MaterialTheme.typography.titleLarge
    )
}