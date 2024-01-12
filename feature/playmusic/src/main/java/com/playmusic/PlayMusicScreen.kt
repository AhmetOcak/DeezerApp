package com.playmusic

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerScaffold
import com.designsystem.icons.DeezerIcons

@Composable
fun PlayMusicScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    viewModel: PlayMusicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = Brush.linearGradient(uiState.imageColors))
    )

    DeezerScaffold(
        modifier = modifier,
        topBar = {
            Row {
                IconButton(onClick = upPress) {
                    Icon(
                        imageVector = DeezerIcons.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        backgroundColor = Color.Transparent
    ) { paddingValues ->
        PlayMusicContent(
            modifier = Modifier.padding(paddingValues),
            imageUrl = "https://images.unsplash.com/photo-1493612276216-ee3925520721?w=500&auto=format&fit=crop&q=60&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8cmFuZG9tfGVufDB8fDB8fHww",
            onPainterStateSuccess = remember(viewModel) { { viewModel.createPalette(it.toBitmap()) } },
            songName = "This is song name",
            artistName = "Artist",
            playIconTint = uiState.imageColors.first()
        )
    }
}

@Composable
private fun PlayMusicContent(
    modifier: Modifier,
    imageUrl: String,
    onPainterStateSuccess: (Drawable) -> Unit,
    songName: String,
    artistName: String,
    playIconTint: Color
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier.size(LocalConfiguration.current.screenWidthDp.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            AnimatedImage(
                modifier = Modifier.fillMaxSize(),
                imageUrl = imageUrl,
                onPainterStateSuccess = onPainterStateSuccess
            )
        }
        Column {
            Text(
                text = songName,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = Color.White
                )
            )
            Text(
                text = artistName,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = Color.White.copy(alpha = 0.6f),
                    fontWeight = FontWeight.W600
                )
            )
        }
        Column {
            MusicSlider()
        }
        PlayerSection(playIconTint = playIconTint)
    }
}

@Composable
private fun PlayerSection(playIconTint: Color) {
    val iconSize = 36.dp
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = DeezerIcons.SkipPrevious,
                contentDescription = null,
                tint = Color.White
            )
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            border = null
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = DeezerIcons.Play,
                contentDescription = null,
                tint = playIconTint
            )
        }
        IconButton(onClick = { /*TODO*/ }) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = DeezerIcons.SkipNext,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun MusicSlider() {
    Column {
        Slider(
            value = 0f,
            onValueChange = {},
            valueRange = 0f..30f,
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f)
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val style = MaterialTheme.typography.titleSmall.copy(
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.W400
            )
            Text(text = "0:00", style = style)
            Text(text = "0:30", style = style)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PlayMusicPreview() {
    Surface {
        PlayMusicContent(
            modifier = Modifier,
            imageUrl = "",
            onPainterStateSuccess = {},
            songName = "This is a preview music name",
            artistName = "Preview artist",
            playIconTint = Color.Red
        )
    }
}