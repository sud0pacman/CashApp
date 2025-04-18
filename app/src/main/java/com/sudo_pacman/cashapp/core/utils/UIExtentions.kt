package com.sudo_pacman.cashapp.core.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Int.VerticalSpace() {
    return Spacer(modifier = Modifier.height(height = this.dp))
}

@Composable
fun Int.HorizontalSpace() {
    return Spacer(modifier = Modifier.width(width = this.dp))
}

@Composable
fun Double.VerticalSpace() {
    return Spacer(modifier = Modifier.height(height = this.dp))
}

@Composable
fun Double.HorizontalSpace() {
    return Spacer(modifier = Modifier.width(width = this.dp))
}

