package com.sudo_pacman.cashapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White

private val DarkColorScheme = darkColorScheme(
    primary = primaryColor,
    background = backgroundColor,
    surface = DarkGray,
    onPrimary = White,
    onBackground = White,
    onSurface = White
)

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
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
