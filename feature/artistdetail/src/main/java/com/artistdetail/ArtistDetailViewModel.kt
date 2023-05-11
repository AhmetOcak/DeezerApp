package com.artistdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.usecases.GetArtistDetailUseCase
import com.usecases.GetTrackListUseCase
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
    getTrackListUseCase: GetTrackListUseCase
) : ViewModel(){

    private val _artistDetailState = MutableStateFlow<ArtistDetailState>(ArtistDetailState.Loading)
    val artistDetailState = _artistDetailState.asStateFlow()

    private val _trackList = getTrackListUseCase(artistId = 8354140).cachedIn(viewModelScope)
    val trackList = _trackList

    var artistName: String = ""
        private set

    init {
        getArtistDetails()
    }

    private fun getArtistDetails() = viewModelScope.launch(Dispatchers.IO) {
        getArtistDetailUseCase(8354140).collect() { response ->
            when(response) {
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

}