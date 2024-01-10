package com.usecases.utils

import android.util.Log
import com.usecases.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

suspend inline fun <T> dbCall(crossinline call: suspend () -> T): Flow<Response<T>> = flow {
    try {
        emit(Response.Success(data = call()))
    } catch (e: Exception) {
        Log.e("DB CALL EXCEPTION", e.stackTraceToString())
        emit(Response.Error(errorMessageId = R.string.unknown_error))
    }
}