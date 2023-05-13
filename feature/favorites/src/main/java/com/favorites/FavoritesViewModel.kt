package com.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.usecases.DeleteFavoriteSongUseCase
import com.usecases.GetAllFavoriteSongsUseCase
import com.usecases.common.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getAllFavoriteSongsUseCase: GetAllFavoriteSongsUseCase,
    private val deleteFavoriteSongUseCase: DeleteFavoriteSongUseCase
) : ViewModel() {

    private val _favoritesState = MutableStateFlow<FavoritesState>(FavoritesState.Nothing)
    val favoritesState = _favoritesState.asStateFlow()

    private val _deleteState = MutableStateFlow<DeleteState>(DeleteState.Nothing)
    val deleteState = _deleteState.asStateFlow()

    init {
        getAllFavoriteSongs()
    }

    fun getAllFavoriteSongs() = viewModelScope.launch(Dispatchers.IO) {
        getAllFavoriteSongsUseCase().collect() { response ->
            when (response) {
                is Response.Loading -> {
                    _favoritesState.value = FavoritesState.Loading
                }

                is Response.Success -> {
                    _favoritesState.value = FavoritesState.Success(data = response.data)
                }

                is Response.Error -> {
                    _favoritesState.value = FavoritesState.Error(message = response.errorMessage)
                }
            }
        }
    }

    fun removeFavoriteSong(songId: Int) = viewModelScope.launch(Dispatchers.IO) {
         deleteFavoriteSongUseCase(songId).collect() { response ->
            when(response) {
                is Response.Loading -> {}
                is Response.Success -> {
                    _deleteState.value = DeleteState.Success("The song has been successfully removed from favorites.")
                }
                is Response.Error -> {
                    _deleteState.value = DeleteState.Error(response.errorMessage)
                }
            }
        }
    }

    fun resetDeleteState() { _deleteState.value = DeleteState.Nothing }
}