package com.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.designsystem.components.AnimatedImage

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DeezerResourceCard(
    onClick: () -> Unit,
    resourceImgUrl: String,
    resourceName: String,
    cardPadding: PaddingValues = PaddingValues(0.dp)
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier
            .size(LocalConfiguration.current.screenWidthDp.dp / 2 - 24.dp)
            .padding(cardPadding),
        shape = RoundedCornerShape(10)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            ResourceImage(
                modifier = Modifier.fillMaxSize(),
                categoryImgUrl = resourceImgUrl
            )
            ResourceName(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                name = resourceName
            )
        }
    }
}

@Composable
private fun ResourceImage(modifier: Modifier, categoryImgUrl: String) {
    AnimatedImage(modifier = modifier, imageUrl = categoryImgUrl)
}

@Composable
private fun ResourceName(modifier: Modifier, name: String) {
    Text(
        modifier = modifier,
        text = name,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
    )
}