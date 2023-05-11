package com.artists

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.GetArtistsUseCase
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
    private val getArtistsUseCase: GetArtistsUseCase
) : ViewModel() {

    private val _artistState = MutableStateFlow<ArtistState>(ArtistState.Loading)
    val artistState = _artistState.asStateFlow()

    init {
        getArtists()
    }

    private fun getArtists() = viewModelScope.launch(Dispatchers.IO) {
        getArtistsUseCase(116)
            .flowOn(Dispatchers.IO)
            .collect() { response ->
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