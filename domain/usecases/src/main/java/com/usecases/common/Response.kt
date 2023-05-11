package com.usecases.common

sealed interface Response<out T> {
    class Success<T>(val data: T) : Response<T>
    class Error<T>(val errorMessage: String) : Response<T>
    object Loading : Response<Nothing>
}