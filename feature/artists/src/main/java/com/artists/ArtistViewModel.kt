package com.artists

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmetocak.common.Response
import com.ahmetocak.common.UiText
import com.ahmetocak.domain.artists.GetArtistsUseCase
import com.ahmetocak.models.ArtistData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase,
    savedStateHandle: SavedStateHandle,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _uiState = MutableStateFlow<ArtistUiState>(ArtistUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getArtists(genreId = checkNotNull(savedStateHandle.get<Long>("genre_id")))
    }

    private fun getArtists(genreId: Long) {
        viewModelScope.launch(ioDispatcher) {
            _uiState.value = ArtistUiState.Loading
            when (val response = getArtistsUseCase(genreId)) {
                is Response.Success -> {
                    _uiState.value = ArtistUiState.Success(artistList = response.data.data)
                }

                is Response.Error -> {
                    _uiState.value = ArtistUiState.Error(message = response.errorMessage)
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