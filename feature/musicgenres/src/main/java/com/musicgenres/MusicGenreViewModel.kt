package com.musicgenres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.models.Data
import com.usecases.musicgenres.GetMusicGenresUseCase
import com.usecases.common.Response
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
            getMusicGenresUseCase().collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.value = MusicGenresUiState.Loading
                    }

                    is Response.Success -> {
                        _uiState.value =
                            MusicGenresUiState.Success(musicGenresList = response.data.data)
                    }

                    is Response.Error -> {
                        _uiState.value = MusicGenresUiState.Error(message = response.errorMessage)
                    }
                }
            }
        }
    }
}

sealed interface MusicGenresUiState {
    object Loading : MusicGenresUiState
    data class Success(val musicGenresList: List<Data>) : MusicGenresUiState
    data class Error(val message: String) : MusicGenresUiState
}