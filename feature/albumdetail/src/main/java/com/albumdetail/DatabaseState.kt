package com.albumdetail

sealed interface DatabaseState {
    object Nothing : DatabaseState
    object Loading : DatabaseState
    data class Success(val message: String) : DatabaseState
    data class Error(val message: String) : DatabaseState
}