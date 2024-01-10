package com.favorites

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.models.FavoriteSongs
import com.usecases.common.Response
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
                    is Response.Loading -> {}

                    is Response.Success -> {
                        _uiState.update {
                            it.copy(favoriteSongsList = response.data)
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(userMessages = listOf(response.errorMessage))
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
                    is Response.Loading -> {}
                    is Response.Success -> {
                        _uiState.update {
                            it.copy(userMessages = listOf("The song has been successfully removed from favorites."))
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(userMessages = listOf(response.errorMessage))
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
    val userMessages: List<String> = listOf(),
    val favoriteSongsList: List<FavoriteSongs> = listOf()
)