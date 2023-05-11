package com.designsystem.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import com.designsystem.theme.DeepOrange

fun LazyListScope.onLoadState(
    modifier: Modifier,
    loadState: LoadState
) {
    when (loadState) {
        is LoadState.Error -> {
            item {

            }
        }

        is LoadState.Loading -> {
            item {
                Box(modifier = modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = DeepOrange)
                }
            }
        }

        else -> {}
    }
}