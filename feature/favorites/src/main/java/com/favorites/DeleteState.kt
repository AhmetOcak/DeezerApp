package com.favorites

sealed interface DeleteState {
    object Nothing: DeleteState
    data class Success(val message: String): DeleteState
    data class Error(val error: String): DeleteState
}