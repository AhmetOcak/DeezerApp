package com.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun ErrorBox(modifier: Modifier = Modifier, errorMessage: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ErrorImage()
        ErrorMessage(errorMessage = errorMessage)
    }
}

@Composable
private fun ErrorImage() {
    Image(
        modifier = Modifier.size(96.dp),
        painter = painterResource(id = R.drawable.error),
        contentDescription = null
    )
}

@Composable
private fun ErrorMessage(errorMessage: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp)
            .padding(top = 16.dp),
        text = errorMessage,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayLarge
    )
}