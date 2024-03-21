package com.albumdetail

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetocak.common.Response
import com.ahmetocak.common.UiText
import com.ahmetocak.domain.GetAllFavoriteSongsUseCase
import com.designsystem.utils.generatePaletteFromImage
import com.ahmetocak.domain.albumdetail.AddFavoriteSongUseCase
import com.ahmetocak.domain.albumdetail.DeleteFavoriteSongUseCase
import com.ahmetocak.domain.albumdetail.GetAlbumDetailsUseCase
import com.ahmetocak.models.FavoriteSongs
import com.ahmetocak.models.AlbumDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val addFavoriteSongUseCase: AddFavoriteSongUseCase,
    private val deleteFavoriteSongUseCase: DeleteFavoriteSongUseCase,
    savedStateHandle: SavedStateHandle,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow(AlbumDetailsUiState())
    val uiState: StateFlow<AlbumDetailsUiState> = _uiState.asStateFlow()

    init {
        getAllFavoriteSongs()
        getAlbumDetails(checkNotNull(savedStateHandle.get<Long>("album_id")))
    }

    private fun getAlbumDetails(albumId: Long) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.update {
                it.copy(detailState = DetailsState.Loading)
            }
            when (val response = getAlbumDetailsUseCase(albumId)) {
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

    private fun getAllFavoriteSongs() {
        viewModelScope.launch(ioDispatcher) {
            when (val response = getAllFavoriteSongsUseCase()) {
                is Response.Success -> {
                    response.data.collect { favoriteSongs ->
                        _uiState.update {
                            it.copy(favoriteSongs = favoriteSongs)
                        }
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

    fun addFavoriteSong(favoriteSongs: FavoriteSongs) {
        viewModelScope.launch(ioDispatcher) {
            when (val response = addFavoriteSongUseCase(favoriteSongs)) {
                is Response.Success -> {
                    _uiState.update {
                        it.copy(userMessages = listOf(UiText.StringResource(R.string.fav_song_added_message)))
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

    fun removeFavoriteSong(songId: Long) {
        viewModelScope.launch(ioDispatcher) {
            when (val response = deleteFavoriteSongUseCase(songId)) {
                is Response.Success -> {
                    _uiState.update {
                        it.copy(userMessages = listOf(UiText.StringResource(R.string.fav_song_removed_message)))
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

    fun isSongAvailableInFavorites(songId: Long): Boolean {
        return if (_uiState.value.isDatabaseAvailable) {
            _uiState.value.favoriteSongs.any { it.id == songId }
        } else {
            false
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

    fun createPalette(bitmap: Bitmap) {
        generatePaletteFromImage(
            bitmap,
            onResult = { colorList ->
                _uiState.update {
                    it.copy(imageColor = colorList)
                }
            }
        )
    }
}

data class AlbumDetailsUiState(
    val errorMessages: List<UiText> = listOf(),
    val userMessages: List<UiText> = listOf(),
    val favoriteSongs: List<FavoriteSongs> = listOf(),
    val albumName: String = "",
    val isDatabaseAvailable: Boolean = true,
    val detailState: DetailsState = DetailsState.Loading,
    val imageColor: List<Color> = listOf(
        Color.Transparent,
        Color.Transparent
    )
)

sealed interface DetailsState {
    object Loading : DetailsState
    data class Success(val data: AlbumDetails) : DetailsState
    data class Error(val message: UiText) : DetailsState
}