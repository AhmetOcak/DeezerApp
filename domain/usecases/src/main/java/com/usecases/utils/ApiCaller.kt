package com.usecases.utils

import android.util.Log
import com.usecases.R
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

suspend inline fun <T> apiCall(crossinline call: suspend () -> T): Flow<Response<T>> = flow {
    try {
        emit(Response.Success(data = call()))
    } catch (e: IOException) {
        Log.e("API CALL EXCEPTION", e.stackTraceToString())
        emit(Response.Error(errorMessageId = R.string.network_error))
    } catch (e: Exception) {
        Log.e("API CALL EXCEPTION", e.stackTraceToString())
        emit(Response.Error(errorMessageId = R.string.unknown_error))
    }
}