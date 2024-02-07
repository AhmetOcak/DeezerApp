package com.designsystem.utils

import androidx.compose.runtime.Immutable

@Immutable
data class Music(
    val imageUrl: String,
    val name: String,
    val artistName: String,
    val audioUrl: String
)