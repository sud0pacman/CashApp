package com.sudo_pacman.cashapp.ui.screen.wallet_screen.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WalletBalanceLayout(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 16.dp,
    balance: String,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius)) // radius
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF1B1B1B),
                        Color(0xFF3C3C3C)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = onClick
            )
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        content = {
            Column {
                Text(
                    "Balance",
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500,

                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    balance,
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.W500,
                )
            }
        }
    )
}
