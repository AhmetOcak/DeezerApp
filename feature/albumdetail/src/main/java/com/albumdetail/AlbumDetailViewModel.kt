package com.albumdetail

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
    private val getAlbumDetailsUseCase: GetAlbumDetailsUseCase
) : ViewModel() {

    private val _albumDetailsState = MutableStateFlow<AlbumDetailsState>(AlbumDetailsState.Loading)
    val albumDetailsState = _albumDetailsState.asStateFlow()

    init {
        getAlbumDetails()
    }

    var albumName: String = ""
        private set

    private fun getAlbumDetails() = viewModelScope.launch(Dispatchers.IO) {
        getAlbumDetailsUseCase(albumId = 175654362).collect() { response ->
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