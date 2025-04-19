package com.sudo_pacman.cashapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White

private val LightColorScheme = lightColorScheme(
    primary = primaryColor,
    background = backgroundColor,
    surface = White,
    onPrimary = White,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun CashAppTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
