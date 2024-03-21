package com.ahmetocak.common

sealed interface Response<out T> {
    class Success<T>(val data: T) : Response<T>
    class Error<T>(val errorMessage: UiText) : Response<T>
}