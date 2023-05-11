package com.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.designsystem.components.DeezerCircularProgressIndicator
import com.designsystem.theme.DeepOrange

@Composable
fun FullScreenProgIndicator(color: Color = DeepOrange) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        DeezerCircularProgressIndicator(color = color)
    }
}