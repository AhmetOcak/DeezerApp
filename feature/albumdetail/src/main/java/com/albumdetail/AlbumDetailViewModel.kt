package com.albumdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.GetAlbumDetailsUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AlbumDetailViewModel @Inject constructor(
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _albumDetailsState = MutableStateFlow<AlbumDetailsState>(AlbumDetailsState.Loading)
    val albumDetailsState = _albumDetailsState.asStateFlow()

    var albumName: String = ""
        private set

    init {
        getAlbumDetails(checkNotNull(savedStateHandle.get<Int>("album_id")))
    }

    private fun getAlbumDetails(albumId: Int) = viewModelScope.launch(Dispatchers.IO) {
        getAlbumDetailsUseCase(albumId).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _albumDetailsState.value = AlbumDetailsState.Loading
                }
                is Response.Success -> {
                    albumName = response.data.title
                    _albumDetailsState.value = AlbumDetailsState.Success(data = response.data)
                }
                is Response.Error ->  {
                    _albumDetailsState.value = AlbumDetailsState.Error(message = response.errorMessage)
                }
            }
        }
    }
}