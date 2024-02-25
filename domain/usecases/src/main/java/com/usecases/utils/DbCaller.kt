package com.usecases.utils

import com.usecases.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun <T> dbCall(crossinline call: suspend () -> T): Flow<Response<T>> = flow {
    try {
        emit(Response.Success(data = call()))
    } catch (e: Exception) {
        emit(Response.Error(errorMessageId = R.string.unknown_error))
    }
}