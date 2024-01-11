package com.designsystem.utils

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.palette.graphics.Palette

fun generatePaletteFromImage(bitmap: Bitmap, onResult: (List<Color>) -> Unit) {
    val argbList = mutableListOf<Color>()
    Palette.from(bitmap).generate { palette ->
        palette?.apply {
            lightVibrantSwatch?.rgb?.let { argbList.add(Color(it)) }
            vibrantSwatch?.rgb?.let { argbList.add(Color(it)) }
            darkVibrantSwatch?.rgb?.let { argbList.add(Color(it)) }
            lightMutedSwatch?.rgb?.let { argbList.add(Color(it)) }
            mutedSwatch?.rgb?.let { argbList.add(Color(it)) }
            darkMutedSwatch?.rgb?.let { argbList.add(Color(it)) }
        }

        // There must be at least 2 colors in the gradient list.
        if (argbList.size == 0) {
            argbList.add(Color.Transparent)
            argbList.add(Color.Transparent)
        } else if (argbList.size == 1) {
            argbList.add(Color.Transparent)
        }

        onResult(argbList)
    }
}