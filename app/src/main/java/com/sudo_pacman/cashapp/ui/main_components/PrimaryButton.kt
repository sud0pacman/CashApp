package com.sudo_pacman.cashapp.ui.main_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.sudo_pacman.cashapp.ui.theme.primaryColor

@Composable
fun PrimaryButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable BoxScope.() -> Unit,
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
//        colors = ButtonColors(
//            contentColor = primaryColor,
//            containerColor = TODO(),
//            disabledContainerColor = TODO(),
//            disabledContentColor = TODO(),
//        )
    ) {
        Box(
            modifier = Modifier, // kerak bo‘lsa modifier bering
            content = content
        )
    }
}
