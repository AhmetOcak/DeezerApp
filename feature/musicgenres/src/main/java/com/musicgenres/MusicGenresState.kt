package com.musicgenres

import com.model.Data

sealed interface MusicGenresState {
    object Loading : MusicGenresState
    data class Success(val data: ArrayList<Data>) : MusicGenresState
    data class Error(val message: String) : MusicGenresState
}