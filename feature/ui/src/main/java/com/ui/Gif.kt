package com.ui

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest

val GIF_HEIGHT = 32.dp

@Composable
fun Gif(modifier: Modifier = Modifier, context: Context) {
    val imageLoader = ImageLoader.Builder(context)
        .components {
            if (SDK_INT >= 28) {
                add(ImageDecoderDecoder.Factory())
            } else {
                add(GifDecoder.Factory())
            }
        }.build()

    Image(
        modifier = modifier
            .fillMaxWidth()
            .height(GIF_HEIGHT),
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(context).data(R.drawable.playing).build(),
            imageLoader = imageLoader
        ),
        contentScale = ContentScale.Crop,
        contentDescription = null
    )
}