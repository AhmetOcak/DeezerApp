package com.designsystem.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.designsystem.theme.DeepOrange

@Composable
fun DeezerCircularProgressIndicator(color: Color = DeepOrange) {
    CircularProgressIndicator(color = color)
}