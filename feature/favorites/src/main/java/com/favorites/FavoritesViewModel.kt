package com.favorites

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.favorites.DeleteFavoriteSongUseCase
import com.usecases.favorites.GetAllFavoriteSongsUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val deleteFavoriteSongUseCase: DeleteFavoriteSongUseCase
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<FavoritesState>(FavoritesState.Nothing)
    val favoritesState = _favoritesState.asStateFlow()

    private val _deleteState = MutableStateFlow<DeleteState>(DeleteState.Nothing)
    val deleteState = _deleteState.asStateFlow()

    var mediaPlayer: MediaPlayer = MediaPlayer()
        private set

    var isAudioPlaying by mutableStateOf(false)
        private set

    init {
        getAllFavoriteSongs()

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    fun getAllFavoriteSongs() = viewModelScope.launch(Dispatchers.IO) {
        getAllFavoriteSongsUseCase().collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _favoritesState.value = FavoritesState.Loading
                }

                is Response.Success -> {
                    _favoritesState.value = FavoritesState.Success(data = response.data)
                }

                is Response.Error -> {
                    _favoritesState.value = FavoritesState.Error(message = response.errorMessage)
                }
            }
        }
    }

    fun removeFavoriteSong(songId: Long) = viewModelScope.launch(Dispatchers.IO) {
         deleteFavoriteSongUseCase(songId).collect() { response ->
            when(response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    _deleteState.value = DeleteState.Success("The song has been successfully removed from favorites.")
                }
                is Response.Error -> {
                    _deleteState.value = DeleteState.Error(response.errorMessage)
                }
            }
        }
    }

    fun playAudio(audioUrl: String) {
        try {
            if (mediaPlayer.isPlaying) {
                isAudioPlaying = true

                mediaPlayer.stop()
                mediaPlayer.reset()
                mediaPlayer.setDataSource(audioUrl)
                mediaPlayer.prepare()
                mediaPlayer.start()
            } else {
                isAudioPlaying = true

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
            isAudioPlaying = false
            mediaPlayer.pause()
        } else {
            isAudioPlaying = true
            mediaPlayer.start()
        }
    }

    fun closeMediaPlayer() {
        isAudioPlaying = false
        if (mediaPlayer.isPlaying) {
            mediaPlayer.stop()
            mediaPlayer.reset()
        }
    }

    fun resetDeleteState() { _deleteState.value = DeleteState.Nothing }

    override fun onCleared() {
        super.onCleared()

        mediaPlayer.release()
    }
}