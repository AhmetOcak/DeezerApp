package com.artistdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.artistdetail.GetArtistAlbumsUseCase
import com.usecases.artistdetail.GetArtistDetailUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase,
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _artistDetailState = MutableStateFlow<ArtistDetailState>(ArtistDetailState.Loading)
    val artistDetailState = _artistDetailState.asStateFlow()

    private val _artistAlbumsState = MutableStateFlow<ArtistAlbumsState>(ArtistAlbumsState.Loading)
    val artistAlbumsState = _artistAlbumsState.asStateFlow()

    var artistName: String = ""
        private set

    init {
        getArtistDetails(checkNotNull(savedStateHandle.get<Long>("artist_id")))
        getArtistAlbums(checkNotNull(savedStateHandle.get<Long>("artist_id")))
    }

    private fun getArtistDetails(artistId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getArtistDetailUseCase(artistId).collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _artistDetailState.value = ArtistDetailState.Loading
                }

                is Response.Success -> {
                    artistName = response.data.name

                    _artistDetailState.value = ArtistDetailState.Success(data = response.data)
                }

                is Response.Error -> {
                    _artistDetailState.value = ArtistDetailState.Error(message = response.errorMessage)
                }
            }
        }
    }

    private fun getArtistAlbums(artistId: Long) = viewModelScope.launch(Dispatchers.IO) {
        getArtistAlbumsUseCase(artistId).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _artistAlbumsState.value = ArtistAlbumsState.Loading
                }
                is Response.Success -> {
                    _artistAlbumsState.value = ArtistAlbumsState.Success(data = response.data)
                }
                is Response.Error -> {
                    _artistAlbumsState.value = ArtistAlbumsState.Error(message = response.errorMessage)
                }
            }
        }
    }
}