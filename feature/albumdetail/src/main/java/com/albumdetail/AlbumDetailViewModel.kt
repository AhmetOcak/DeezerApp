package com.albumdetail

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.models.FavoriteSongs
import com.models.albumdetail.AlbumDetails
import com.usecases.albumdetail.AddFavoriteSongUseCase
import com.usecases.albumdetail.DeleteFavoriteSongUseCase
import com.usecases.albumdetail.GetAlbumDetailsUseCase
import com.usecases.albumdetail.GetAllFavoriteSongsUseCase
import com.usecases.common.Response
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
class AlbumDetailViewModel @Inject constructor(
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val addFavoriteSongUseCase: AddFavoriteSongUseCase,
    private val deleteFavoriteSongUseCase: DeleteFavoriteSongUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var mediaPlayer: MediaPlayer = MediaPlayer()

    private val _uiState = MutableStateFlow(AlbumDetailsUiState())
    val uiState: StateFlow<AlbumDetailsUiState> = _uiState.asStateFlow()

    init {
        getAllFavoriteSongs()
        getAlbumDetails(checkNotNull(savedStateHandle.get<Long>("album_id")))

        mediaPlayer.setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build()
        )
    }

    private fun getAlbumDetails(albumId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getAlbumDetailsUseCase(albumId).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update {
                            it.copy(detailState = DetailsState.Loading)
                        }
                    }

                    is Response.Success -> {
                        _uiState.update {
                            it.copy(
                                albumName = response.data.title,
                                detailState = DetailsState.Success(response.data)
                            )
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(detailState = DetailsState.Error(message = response.errorMessage))
                        }
                    }
                }
            }
        }
    }

    private fun getAllFavoriteSongs() {
        viewModelScope.launch(Dispatchers.IO) {
            getAllFavoriteSongsUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        _uiState.update {
                            it.copy(favoriteSongs = response.data)
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(isDatabaseAvailable = false)
                        }
                    }
                }
            }
        }
    }

    fun addFavoriteSong(favoriteSongs: FavoriteSongs) {
        viewModelScope.launch(Dispatchers.IO) {
            addFavoriteSongUseCase(favoriteSongs).collect { response ->
                when (response) {
                    is Response.Loading -> {}

                    is Response.Success -> {
                        _uiState.update {
                            it.copy(userMessages = listOf("The song has been successfully added to favorites."))
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(errorMessages = listOf(response.errorMessage))
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
                            it.copy(errorMessages = listOf(response.errorMessage))
                        }
                    }
                }
            }
        }
    }

    fun isSongAvailableInFavorites(songId: Long): Boolean {
        return if (_uiState.value.isDatabaseAvailable) {
            _uiState.value.favoriteSongs.any { it.id == songId }
        } else {
            false
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
            _uiState.update {
                it.copy(errorMessages = listOf(e.message ?: "Something went wrong"))
            }
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

    fun consumedUserMessages() {
        _uiState.update {
            it.copy(userMessages = listOf())
        }
    }

    override fun onCleared() {
        super.onCleared()

        mediaPlayer.release()
    }

}

data class AlbumDetailsUiState(
    val isAudioPlaying: Boolean = false,
    val errorMessages: List<String> = listOf(),
    val userMessages: List<String> = listOf(),
    val favoriteSongs: List<FavoriteSongs> = listOf(),
    val albumName: String = "",
    val isDatabaseAvailable: Boolean = true,
    val detailState: DetailsState = DetailsState.Loading
)

sealed interface DetailsState {
    object Loading : DetailsState
    data class Success(val data: AlbumDetails) : DetailsState
    data class Error(val message: String) : DetailsState
}