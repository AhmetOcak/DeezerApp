package com.artistdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.models.ArtistAlbums
import com.models.ArtistDetail
import com.usecases.artistdetail.GetArtistAlbumsUseCase
import com.usecases.artistdetail.GetArtistDetailUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistDetailViewModel @Inject constructor(
    private val getArtistDetailUseCase: GetArtistDetailUseCase,
    private val getArtistAlbumsUseCase: GetArtistAlbumsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ArtistDetailUiState())
    val uiState: StateFlow<ArtistDetailUiState> = _uiState.asStateFlow()

    init {
        getArtistDetails(checkNotNull(savedStateHandle.get<Long>("artist_id")))
        getArtistAlbums(checkNotNull(savedStateHandle.get<Long>("artist_id")))
    }

    private fun getArtistDetails(artistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getArtistDetailUseCase(artistId).collect { response ->
                when (response) {
                    is Response.Loading -> {
                        _uiState.update {
                            it.copy(detailState = DetailState.Loading)
                        }
                    }

                    is Response.Success -> {
                        _uiState.update {
                            it.copy(
                                artistName = response.data.name,
                                detailState = DetailState.Success(data = response.data)
                            )
                        }
                    }

                    is Response.Error -> {
                        _uiState.update {
                            it.copy(detailState = DetailState.Error(message = response.errorMessage))
                        }
                    }
                }
            }
        }
    }

    private fun getArtistAlbums(artistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getArtistAlbumsUseCase(artistId).collect { response ->
                when(response) {
                    is Response.Loading -> {
                        _uiState.update {
                            it.copy(albumsState = AlbumsState.Loading)
                        }
                    }
                    is Response.Success -> {
                        _uiState.update {
                            it.copy(albumsState = AlbumsState.Success(albumsList = response.data))
                        }
                    }
                    is Response.Error -> {
                        _uiState.update {
                            it.copy(albumsState = AlbumsState.Error(message = response.errorMessage))
                        }
                    }
                }
            }
        }
    }
}

data class ArtistDetailUiState(
    val artistName: String = "",
    val detailState: DetailState = DetailState.Loading,
    val albumsState: AlbumsState = AlbumsState.Loading
)

sealed interface DetailState {
    object Loading : DetailState
    data class Success(val data: ArtistDetail) : DetailState
    data class Error(val message: String) : DetailState
}

sealed interface AlbumsState {
    object Loading: AlbumsState
    data class Success(val albumsList: ArtistAlbums): AlbumsState
    data class Error(val message: String): AlbumsState
}