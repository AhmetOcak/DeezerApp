package com.designsystem.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.designsystem.R

/**
 * A method for the load image from the network in an animated way.
 * This method displays a circular progress indicator while loading image from the network.
 * If loading the image fail, method displays an error image.
 *
 *  @param modifier the [Modifier] to be applied for this method.
 *  @param imageUrl url of the image.
 */
@Composable
fun AnimatedImage(modifier: Modifier = Modifier, imageUrl: String) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .size(Size.ORIGINAL)
            .build()
    )

    val transition by animateFloatAsState(
        targetValue = if (painter.state is AsyncImagePainter.State.Success) 1f else 0f
    )

    val matrix = ColorMatrix()
    matrix.setToSaturation(transition)

    when (painter.state) {
        is AsyncImagePainter.State.Loading -> {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is AsyncImagePainter.State.Error -> { ErrorImage(modifier) }
        is AsyncImagePainter.State.Empty -> { EmptyImage(modifier) }
        else -> { AnimatedImage(modifier, transition, painter, matrix) }
    }
}

@Composable
private fun AnimatedImage(
    modifier: Modifier,
    transition: Float,
    painter: AsyncImagePainter,
    matrix: ColorMatrix
) {
    Image(
        modifier = modifier
            .scale(.8f + (.2f * transition))
            .graphicsLayer { rotationX = (1f - transition) * 5f }
            .alpha(1f.coerceAtMost(transition / .2f)),
        painter = painter,
        contentDescription = "Artist image",
        contentScale = ContentScale.Crop,
        colorFilter = ColorFilter.colorMatrix(colorMatrix = matrix)
    )
}

@Composable
private fun EmptyImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        contentDescription = "No Image Available",
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.no_image_available)
    )
}

@Composable
private fun ErrorImage(modifier: Modifier) {
    Image(
        modifier = modifier,
        contentDescription = "The image could not be loaded.",
        contentScale = ContentScale.Fit,
        painter = painterResource(id = R.drawable.error_image)
    )
}