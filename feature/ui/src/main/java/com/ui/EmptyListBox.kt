package com.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun EmptyListBox(
    modifier: Modifier,
    message: String,
    imgSize: Dp = 96.dp
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        EmptyBoxImg(imgSize)
        Message(message)
    }
}

@Composable
private fun EmptyBoxImg(imgSize: Dp) {
    Image(
        modifier = Modifier.size(imgSize),
        painter = painterResource(id = R.drawable.empty_box),
        contentDescription = null
    )
}

@Composable
private fun Message(message: String) {
    Text(
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 32.dp),
        text = message,
        style = MaterialTheme.typography.displayMedium,
        textAlign = TextAlign.Center
    )
}