package com.favorites

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.designsystem.utils.UiText
import com.models.FavoriteSongs
import com.usecases.utils.Response
import com.usecases.favorites.DeleteFavoriteSongUseCase
import com.usecases.favorites.GetAllFavoriteSongsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val deleteFavoriteSongUseCase: DeleteFavoriteSongUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    init {
        getAllFavoriteSongs()

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    fun getAllFavoriteSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFavoriteSongsUseCase().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _uiState.update {
                            it.copy(favoriteSongsList = response.data)
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(userMessages = listOf(UiText.StringResource(response.errorMessageId)))
                        }
                    }
                }
            }
        }
    }

    fun removeFavoriteSong(songId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteSongUseCase(songId).collect { response ->
                when (response) {
                    is Response.Success -> {
                        _uiState.update {
                            it.copy(userMessages = listOf(UiText.StringResource(R.string.fav_song_removed_message)))
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(userMessages = listOf(UiText.StringResource(response.errorMessageId)))
                        }
                    }
                }
            }
        }
    }

    fun playAudio(audioUrl: String) {
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
        }
    }

    fun pauseAudio() {
        if (mediaPlayer.isPlaying) {
            setAudioNotPlaying()
            mediaPlayer.pause()
        } else {
            setAudioPlaying()
            mediaPlayer.start()
        }
    }

    fun closeMediaPlayer() {
        setAudioNotPlaying()
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    fun consumedUserMessages() {
        _uiState.update {
            it.copy(userMessages = listOf())
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

    override fun onCleared() {
        super.onCleared()

        mediaPlayer.release()
    }
}

data class FavoritesUiState(
    val isAudioPlaying: Boolean = false,
    val userMessages: List<UiText> = listOf(),
    val favoriteSongsList: List<FavoriteSongs> = listOf()
)