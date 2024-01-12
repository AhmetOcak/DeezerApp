package com.playmusic

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.designsystem.utils.generatePaletteFromImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class PlayMusicViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlayMusicUiState())
    val uiState: StateFlow<PlayMusicUiState> = _uiState.asStateFlow()

    fun createPalette(bitmap: Bitmap) {
        generatePaletteFromImage(
            bitmap = bitmap,
            onResult = { colorPalette ->
                _uiState.update {
                    it.copy(imageColors = colorPalette)
                }
            }
        )
    }
}

data class PlayMusicUiState(
    val imageColors: List<Color> = listOf(
        Color.Transparent,
        Color.Transparent
    )
)