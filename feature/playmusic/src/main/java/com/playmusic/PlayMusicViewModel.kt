package com.playmusic

import android.graphics.Bitmap
import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.designsystem.utils.UiText
import com.designsystem.utils.generatePaletteFromImage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.io.IOException
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class PlayMusicViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PlayMusicUiState())
    val uiState: StateFlow<PlayMusicUiState> = _uiState.asStateFlow()

    private val mediaPlayer: MediaPlayer by lazy { MediaPlayer() }

    var currentAudioPosition: Int by mutableIntStateOf(0)
        private set

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

        mediaPlayer.setOnPreparedListener {
            _uiState.update {
                it.copy(audioDuration = mediaPlayer.duration / 1000)
            }
        }

        mediaPlayer.setOnCompletionListener {
            setAudioNotPlaying()
        }

        startAudio(URLDecoder.decode(checkNotNull(savedStateHandle["music_audio_key"]), "UTF-8"))
        initializeMusicInfo()
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

    private fun initializeMusicInfo() {
        _uiState.update {
            it.copy(
                musicName = URLDecoder.decode(
                    checkNotNull(savedStateHandle["music_name_key"]),
                    "UTF-8"
                ),
                albumImg = URLDecoder.decode(
                    checkNotNull(savedStateHandle["album_img_key"]),
                    "UTF-8"
                ),
                artistName = URLDecoder.decode(
                    checkNotNull(savedStateHandle["artist_name_key"]),
                    "UTF-8"
                )
            )
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
        if (mediaPlayer.currentPosition == mediaPlayer.duration) {
            currentAudioPosition = 0
        }
    }

    private fun setAudioNotPlaying() {
        _uiState.update {
            it.copy(isAudioPlaying = false)
        }
    }

    fun seekForwardAudio() {
        if (10 + currentAudioPosition > (mediaPlayer.duration / 1000)) {
            mediaPlayer.seekTo(mediaPlayer.duration)
            currentAudioPosition = (mediaPlayer.duration / 1000)
        } else {
            mediaPlayer.seekTo((10 + currentAudioPosition) * 1000)
            currentAudioPosition += 10
        }
    }

    fun seekRewindAudio() {
        if (currentAudioPosition - 10 < 0) {
            mediaPlayer.seekTo(0)
            currentAudioPosition = 0
        } else {
            mediaPlayer.seekTo((currentAudioPosition - 10) * 1000)
            currentAudioPosition -= 10
        }
    }

    fun increaseAudioPosition() {
        currentAudioPosition++
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
    val albumImg: String? = null,
    val musicName: String? = null,
    val artistName: String? = null,
    val imageColors: List<Color> = listOf(
        Color.Transparent,
        Color.Transparent
    ),
    val audioDuration: Int = 0
)