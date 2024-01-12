package com.playmusic

import android.graphics.Bitmap
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.designsystem.utils.UiText
import com.designsystem.utils.generatePaletteFromImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class PlayMusicViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlayMusicUiState())
    val uiState: StateFlow<PlayMusicUiState> = _uiState.asStateFlow()

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }

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

    init {
        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )

        startAudio("https://cdns-preview-f.dzcdn.net/stream/c-f30e22028688f380ae744148406abf59-4.mp3")
    }

    fun onPlayClick() {
        if (mediaPlayer.isPlaying) {
            setAudioNotPlaying()
            mediaPlayer.pause()
        } else {
            setAudioPlaying()
            mediaPlayer.start()
        }
    }

    private fun startAudio(audioUrl: String) {
        try {
            if (mediaPlayer.isPlaying) {
                setAudioPlaying()

                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } else {
                setAudioPlaying()

                mediaPlayer.reset()
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
            }
        } catch (e: IOException) {
            Log.e("MEDIA PLAYER", e.stackTraceToString())
            _uiState.update {
                it.copy(
                    errorMessages = listOf(
                        UiText.DynamicString(e.message ?: "Something went wrong")
                    )
                )
            }
        }
    }

    private fun setAudioPlaying() {
        _uiState.update {
            it.copy(isAudioPlaying = true)
        }
    }

    private fun setAudioNotPlaying() {
        _uiState.update {
            it.copy(isAudioPlaying = false)
        }
    }

    fun consumedErrorMessages() {
        _uiState.update {
            it.copy(errorMessages = listOf())
        }
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.release()
    }
}

data class PlayMusicUiState(
    val isAudioPlaying: Boolean = false,
    val errorMessages: List<UiText> = listOf(),
    val imageColors: List<Color> = listOf(
        Color.Transparent,
        Color.Transparent
    )
)