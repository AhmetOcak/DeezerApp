package com.designsystem.components

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.paging.compose.LazyPagingItems


/**
 * After recreation, LazyPagingItems first return 0 items, then the cached items.
 * This behavior/issue is resetting the LazyListState scroll position.
 * Below is a workaround. More info:
 * [https://issuetracker.google.com/issues/177245496](https://issuetracker.google.com/issues/177245496).
 * @return if [LazyPagingItems.itemCount] equal 0 then return a different LazyListState instance.
 * Otherwise return rememberLazyListState (normal case).
 */
@Composable
fun <T : Any> LazyPagingItems<T>.rememberLazyListState(): LazyListState {
    return when (itemCount) {
        0 -> remember(this) { LazyListState(0, 0) }
        else -> androidx.compose.foundation.lazy.rememberLazyListState()
    }
}