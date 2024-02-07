package com.playmusic

import android.graphics.drawable.Drawable
import android.widget.Toast
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.designsystem.components.AnimatedImage
import com.designsystem.components.DeezerScaffold
import com.designsystem.icons.DeezerIcons
import kotlinx.coroutines.delay

@Composable
fun PlayMusicScreen(
    modifier: Modifier = Modifier,
    upPress: () -> Unit,
    viewModel: PlayMusicViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    if (uiState.errorMessages.isNotEmpty()) {
        Toast.makeText(
            LocalContext.current,
            uiState.errorMessages.first().asString(),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.consumedErrorMessages()
    }

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
            imageUrl = uiState.albumImg,
            onPainterStateSuccess = remember(viewModel) { { viewModel.createPalette(it.toBitmap()) } },
            songName = uiState.musicName ?: "",
            artistName = uiState.artistName ?: "",
            playIconTint = uiState.imageColors.first(),
            onPlayClick = remember(viewModel) { viewModel::onPlayClick },
            onForwardClick = remember(viewModel) { viewModel::seekForwardAudio },
            onRewindClick = remember(viewModel) { viewModel::seekRewindAudio },
            isAudioPlaying = uiState.isAudioPlaying,
            audioDuration = uiState.audioDuration,
            currentAudioPosition = viewModel.currentAudioPosition,
            increaseCurrentAudioPosition = remember(viewModel) { viewModel::increaseAudioPosition }
        )
    }
}

@Composable
private fun PlayMusicContent(
    modifier: Modifier,
    imageUrl: String?,
    onPainterStateSuccess: (Drawable) -> Unit,
    songName: String,
    artistName: String,
    playIconTint: Color,
    onPlayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onRewindClick: () -> Unit,
    isAudioPlaying: Boolean,
    audioDuration: Int,
    currentAudioPosition: Int,
    increaseCurrentAudioPosition: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Card(
            modifier = Modifier
                .size(LocalConfiguration.current.screenWidthDp.dp)
                .padding(32.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            imageUrl?.let {
                AnimatedImage(
                    modifier = Modifier.fillMaxSize(),
                    imageUrl = imageUrl,
                    onPainterStateSuccess = onPainterStateSuccess
                )
            }
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
            MusicSlider(
                isAudioPlaying = isAudioPlaying,
                audioDuration = audioDuration,
                currentAudioPosition = currentAudioPosition,
                increaseCurrentAudioPosition = increaseCurrentAudioPosition
            )
        }
        PlayerSection(
            playIconTint = playIconTint,
            onPlayClick = onPlayClick,
            onForwardClick = onForwardClick,
            onRewindClick = onRewindClick,
            isAudioPlaying = isAudioPlaying
        )
    }
}

@Composable
private fun PlayerSection(
    playIconTint: Color,
    onPlayClick: () -> Unit,
    onForwardClick: () -> Unit,
    onRewindClick: () -> Unit,
    isAudioPlaying: Boolean
) {
    val iconSize = 36.dp
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onRewindClick) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = DeezerIcons.Rewind,
                contentDescription = null,
                tint = Color.White
            )
        }
        OutlinedButton(
            onClick = onPlayClick,
            shape = CircleShape,
            contentPadding = PaddingValues(16.dp),
            colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.White),
            border = null
        ) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = if (isAudioPlaying) DeezerIcons.Pause else DeezerIcons.Play,
                contentDescription = null,
                tint = playIconTint
            )
        }
        IconButton(onClick = onForwardClick) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = DeezerIcons.Forward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}

@Composable
private fun MusicSlider(
    isAudioPlaying: Boolean,
    audioDuration: Int,
    currentAudioPosition: Int,
    increaseCurrentAudioPosition: () -> Unit
) {
    LaunchedEffect(isAudioPlaying) {
        if (isAudioPlaying && currentAudioPosition <= audioDuration) {
            while (true) {
                delay(1000)
                increaseCurrentAudioPosition()
            }
        }
    }

    Column {
        Slider(
            value = currentAudioPosition.toFloat(),
            onValueChange = {},
            valueRange = 0f..audioDuration.toFloat(),
            colors = SliderDefaults.colors(
                thumbColor = Color.White,
                inactiveTrackColor = Color.White.copy(alpha = 0.3f),
                activeTrackColor = Color.White
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
            Text(
                text = if (currentAudioPosition < 10) {
                    "0:0$currentAudioPosition"
                } else {
                    "0:$currentAudioPosition"
                }, style = style
            )
            Text(text = "0:$audioDuration", style = style)
        }
    }
}