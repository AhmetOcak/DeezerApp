package com.usecases.utils

import com.usecases.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

suspend inline fun <T> apiCall(crossinline call: suspend () -> T): Flow<Response<T>> = flow {
    try {
        emit(Response.Success(data = call()))
    } catch (e: IOException) {
        emit(Response.Error(errorMessageId = R.string.network_error))
    } catch (e: Exception) {
        emit(Response.Error(errorMessageId = R.string.unknown_error))
    }
}