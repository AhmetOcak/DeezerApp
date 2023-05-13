package com.albumdetail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.model.FavoriteSongs
import com.usecases.albumdetail.*
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
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val addFavoriteSongUseCase: AddFavoriteSongUseCase,
    private val deleteFavoriteSongUseCase: DeleteFavoriteSongUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _albumDetailsState = MutableStateFlow<AlbumDetailsState>(AlbumDetailsState.Loading)
    val albumDetailsState = _albumDetailsState.asStateFlow()

    private val _databaseState = MutableStateFlow<DatabaseState>(DatabaseState.Nothing)
    val databaseState = _databaseState.asStateFlow()

    private var favoriteSongs: List<FavoriteSongs> = mutableListOf()

    var databaseStatus by mutableStateOf(true)
        private set

    var albumName: String = ""
        private set

    init {
        getAllFavoriteSongs()
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

    private fun getAllFavoriteSongs() = viewModelScope.launch(Dispatchers.IO) {
        getAllFavoriteSongsUseCase().collect() { response ->
            when(response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    favoriteSongs = response.data
                }
                is Response.Error -> {
                    databaseStatus = false
                }
            }
        }
    }

    fun addFavoriteSong(favoriteSongs: FavoriteSongs) = viewModelScope.launch(Dispatchers.IO) {
        addFavoriteSongUseCase(favoriteSongs).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _databaseState.value = DatabaseState.Loading
                }
                is Response.Success -> {
                    _databaseState.value = DatabaseState.Success("The song has been successfully added to favorites.")
                }
                is Response.Error -> {
                    _databaseState.value = DatabaseState.Error(response.errorMessage)
                }
            }
        }
    }

    fun removeFavoriteSong(songId: Int) = viewModelScope.launch(Dispatchers.IO) {
        deleteFavoriteSongUseCase(songId).collect() { response ->
            when(response) {
                is Response.Loading -> {
                    _databaseState.value = DatabaseState.Loading
                }
                is Response.Success -> {
                    _databaseState.value = DatabaseState.Success("The song has been successfully removed from favorites.")
                }
                is Response.Error -> {
                    _databaseState.value = DatabaseState.Error(response.errorMessage)
                }
            }
        }
    }

    fun isSongAvailableInFavorites(songId: Int): Boolean {
        return if (databaseStatus) {
            favoriteSongs.any { it.id == songId }
        } else {
            false
        }
    }

    fun resetDatabaseState() { _databaseState.value = DatabaseState.Nothing }

}