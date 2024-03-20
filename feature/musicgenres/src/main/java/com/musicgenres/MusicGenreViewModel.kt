package com.musicgenres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.designsystem.utils.UiText
import com.ahmetocak.domain.musicgenres.GetMusicGenresUseCase
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.models.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicGenreViewModel @Inject constructor(
    private val getMusicGenresUseCase: GetMusicGenresUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MusicGenresUiState>(MusicGenresUiState.Loading)
    val uiState: StateFlow<MusicGenresUiState> = _uiState.asStateFlow()

    init {
        getMusicGenres()
    }

    private fun getMusicGenres() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = MusicGenresUiState.Loading
            getMusicGenresUseCase().collect { response ->
                when (response) {
                    is Response.Success -> {
                        _uiState.value =
                            MusicGenresUiState.Success(musicGenresList = response.data.data)
                    }

                    is Response.Error -> {
                        _uiState.value =
                            MusicGenresUiState.Error(message = UiText.StringResource(response.errorMessageId))
                    }
                }
            }
        }
    }
}

sealed interface MusicGenresUiState {
    object Loading : MusicGenresUiState
    data class Success(val musicGenresList: List<Data>) : MusicGenresUiState
    data class Error(val message: UiText) : MusicGenresUiState
}