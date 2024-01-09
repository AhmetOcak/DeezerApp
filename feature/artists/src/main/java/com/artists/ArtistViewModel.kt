package com.artists

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.artists.GetArtistsUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _artistState = MutableStateFlow<ArtistState>(ArtistState.Loading)
    val artistState = _artistState.asStateFlow()

    init {
        getArtists(genreId = checkNotNull(savedStateHandle.get<Long>("genre_id")))
    }

    private fun getArtists(genreId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getArtistsUseCase(genreId)
                .flowOn(Dispatchers.IO)
                .collect { response ->
                    when (response) {
                        is Response.Loading -> {
                            _artistState.value = ArtistState.Loading
                        }

                        is Response.Success -> {
                            _artistState.value = ArtistState.Success(data = response.data.data)
                        }

                        is Response.Error -> {
                            _artistState.value = ArtistState.Error(message = response.errorMessage)
                        }
                    }
                }
        }
    }
}