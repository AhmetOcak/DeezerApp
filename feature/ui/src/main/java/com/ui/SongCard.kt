package com.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.designsystem.components.AnimatedImage
import com.designsystem.icons.DeezerIcons
import com.designsystem.theme.DarkPurple
import com.designsystem.theme.GradientDeepPurple
import com.designsystem.theme.HeartRed

private val cardShape = RoundedCornerShape(10)
private val cardHeight = 96.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SongCard(
    modifier: Modifier,
    songImageUrl: String,
    songName: String,
    duration: String,
    favoriteIconInitVal: Boolean,
    onSongClicked: () -> Unit,
    onFavouriteBtnClicked: () -> Unit,
    showAlbum: Boolean = false,
    albumName: String = ""
) {
    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .height(cardHeight)
            .padding(horizontal = 16.dp)
            .border(width = 1.dp, brush = GradientDeepPurple, shape = cardShape),
        shape = cardShape,
        onClick = onSongClicked
    ) {
        Row(modifier = modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            SongImage(
                modifier = modifier
                    .weight(1f)
                    .fillMaxSize(), songImageUrl = songImageUrl
            )
            SongDetail(
                modifier = modifier
                    .weight(2f)
                    .fillMaxSize(),
                songName = songName,
                duration = duration,
                onFavouriteBtnClicked = onFavouriteBtnClicked,
                favoriteIconInitVal = favoriteIconInitVal,
                showAlbum = showAlbum,
                albumName = albumName
            )
        }
    }
}

@Composable
private fun SongImage(modifier: Modifier, songImageUrl: String) {
    AnimatedImage(
        modifier = modifier,
        imageUrl = songImageUrl
    )
}

@Composable
private fun SongDetail(
    modifier: Modifier,
    songName: String,
    duration: String,
    onFavouriteBtnClicked: () -> Unit,
    favoriteIconInitVal: Boolean,
    showAlbum: Boolean,
    albumName: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(start = 24.dp)
                .weight(8f),
            verticalArrangement = Arrangement.Center
        ) {
            if (showAlbum) {
                SongAlbum(albumName = albumName)
            }
            SongName(songName = songName)
            SongDuration(duration = duration)
        }
        AddFavouritesButton(
            modifier = modifier.weight(2f),
            onFavouriteBtnClicked = onFavouriteBtnClicked,
            favoriteIconInitVal = favoriteIconInitVal
        )
    }
}

@Composable
private fun AddFavouritesButton(
    modifier: Modifier,
    onFavouriteBtnClicked: () -> Unit,
    favoriteIconInitVal: Boolean,
    darkTheme: Boolean = isSystemInDarkTheme()
) {
    var isSongFavourite by rememberSaveable { mutableStateOf(favoriteIconInitVal) }

    IconButton(
        modifier = modifier,
        onClick = {
            onFavouriteBtnClicked()
            isSongFavourite = !isSongFavourite
        }
    ) {
        Icon(
            imageVector = if (isSongFavourite) {
                DeezerIcons.Favorite
            } else {
                DeezerIcons.FavoriteBorder
            },
            contentDescription = null,
            tint = if (isSongFavourite) {
                HeartRed
            } else {
                if (darkTheme) Color.White else DarkPurple
            }
        )
    }
}

@Composable
private fun SongAlbum(albumName: String) {
    Text(
        text = albumName,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
private fun SongName(songName: String) {
    Text(
        text = songName,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.displayMedium
    )
}

@Composable
private fun SongDuration(duration: String) {
    Text(
        text = duration,
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.displaySmall
    )
}