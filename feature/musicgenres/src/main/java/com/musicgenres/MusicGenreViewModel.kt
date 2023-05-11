package com.musicgenres

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.GetMusicGenresUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MusicGenreViewModel @Inject constructor(
    private val getMusicGenresUseCase: GetMusicGenresUseCase
): ViewModel() {

    private val _musicGenresState = MutableStateFlow<MusicGenresState>(MusicGenresState.Loading)
    val musicGenresState = _musicGenresState.asStateFlow()

    init {
        getMusicGenres()
    }
    private fun getMusicGenres() = viewModelScope.launch(Dispatchers.IO) {
        getMusicGenresUseCase().collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _musicGenresState.value = MusicGenresState.Loading
                }
                is Response.Success -> {
                    _musicGenresState.value = MusicGenresState.Success(data = response.data.data)
                }
                is Response.Error -> {
                    _musicGenresState.value = MusicGenresState.Error(message = response.errorMessage)
                }
            }
        }
    }
}