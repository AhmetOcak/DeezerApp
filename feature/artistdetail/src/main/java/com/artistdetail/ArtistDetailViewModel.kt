package com.artistdetail

import android.graphics.Bitmap
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.designsystem.utils.UiText
import com.designsystem.utils.generatePaletteFromImage
import com.ahmetocak.domain.artistdetail.GetArtistAlbumsUseCase
import com.ahmetocak.domain.artistdetail.GetArtistDetailUseCase
import com.ahmetocak.domain.utils.Response
import com.ahmetocak.models.ArtistAlbums
import com.ahmetocak.models.ArtistDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
        val artistId = checkNotNull(savedStateHandle.get<Long>("artist_id"))
        getArtistDetails(artistId)

        _uiState.update {
            it.copy(albumsList = getArtistAlbumsUseCase(artistId).cachedIn(viewModelScope))
        }
    }

    private fun getArtistDetails(artistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update {
                it.copy(detailState = DetailState.Loading)
            }
            getArtistDetailUseCase(artistId).collect { response ->
                when (response) {
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
                            it.copy(
                                detailState = DetailState.Error(
                                    message = UiText.StringResource(
                                        response.errorMessageId
                                    )
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun createPalette(bitmap: Bitmap) {
        generatePaletteFromImage(
            bitmap,
            onResult = { colorList ->
                _uiState.update {
                    it.copy(imageColors = colorList)
                }
            }
        )
    }
}

data class ArtistDetailUiState(
    val artistName: String = "",
    val detailState: DetailState = DetailState.Loading,
    val albumsList: Flow<PagingData<ArtistAlbums>>? = null,
    val imageColors: List<Color> = listOf(
        Color.Transparent,
        Color.Transparent
    )
)

sealed interface DetailState {
    object Loading : DetailState
    data class Success(val data: ArtistDetail) : DetailState
    data class Error(val message: UiText) : DetailState
}