package com.designsystem.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeezerTopAppBar(
    modifier: Modifier = Modifier,
    title: String,
    titleColor: Color = Color.White,
    actions: @Composable (RowScope.() -> Unit) = {},
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    navigationIcon: ImageVector,
    navigationIconTint: Color = MaterialTheme.colorScheme.onSurface,
    navigationContentDescription: String?,
    onNavigateClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title, color = titleColor) },
        actions = actions,
        colors = colors,
        navigationIcon = {
            IconButton(onClick = onNavigateClick) {
                Icon(
                    imageVector = navigationIcon,
                    contentDescription = navigationContentDescription,
                    tint = navigationIconTint
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeezerTopAppBar(
    modifier: Modifier = Modifier,
    actionIcon: ImageVector,
    actionIconTint: Color = MaterialTheme.colorScheme.onSurface,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    logoHeight: Dp = 32.dp,
    @DrawableRes logoId: Int,
    logoContentDescription: String?,
    actionContentDescription: String?,
    onActionClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Image(
                modifier = modifier.height(logoHeight),
                painter = painterResource(id = logoId),
                contentDescription = logoContentDescription,
                contentScale = ContentScale.Fit
            )
        },
        actions = {
            IconButton(onClick = onActionClick) {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = actionContentDescription,
                    tint = actionIconTint
                )
            }
        },
        colors = colors
    )
}