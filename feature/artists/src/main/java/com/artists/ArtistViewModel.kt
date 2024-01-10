package com.artists

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.designsystem.UiText
import com.models.ArtistData
import com.usecases.artists.GetArtistsUseCase
import com.usecases.utils.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArtistUiState>(ArtistUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getArtists(genreId = checkNotNull(savedStateHandle.get<Long>("genre_id")))
    }

    private fun getArtists(genreId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = ArtistUiState.Loading
            getArtistsUseCase(genreId).collect { response ->
                when (response) {
                    is Response.Success -> {
                        _uiState.value = ArtistUiState.Success(artistList = response.data.data)
                    }

                    is Response.Error -> {
                        _uiState.value =
                            ArtistUiState.Error(message = UiText.StringResource(response.errorMessageId))
                    }
                }
            }
        }
    }
}

sealed interface ArtistUiState {
    object Loading : ArtistUiState
    data class Success(val artistList: List<ArtistData>) : ArtistUiState
    data class Error(val message: UiText) : ArtistUiState
}