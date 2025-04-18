package com.sudo_pacman.cashapp.ui.main_components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CircleShadowComponent(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
    size: Dp = 54.dp,
    onClick: (() -> Unit)? = null,
) {
    Box(
        modifier = modifier
    ) {
        Box(
            contentAlignment = Alignment.Center,
        ) {
            // Bottom dark shadow 1
            Box(
                modifier = Modifier
                    .size(size)
                    .offset(y = 1.dp) // pastga suramiz
                    .blur(
                        radius = 2.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
                    .background(
                        color = Color(0x1C19171A).copy(alpha = 0.05f),
                        shape = CircleShape
                    )
            )

            // Bottom dark shadow 2
            Box(
                modifier = Modifier
                    .size(size)
                    .offset(y = 2.dp) // pastga suramiz
                    .blur(
                        radius = 3.dp,
                        edgeTreatment = BlurredEdgeTreatment.Unbounded
                    )
                    .background(
                        color = Color(0x1C19171A).copy(alpha = 0.10f),
                        shape = CircleShape
                    )
            )

            Box(
                modifier = Modifier
                    .size(size - 2.dp)
                    .clip(shape = CircleShape)
                    .background(
                        color = Color.White,
                        shape = CircleShape,
                    )
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(color = Color.Gray),
                        onClick = {
                            onClick?.invoke()
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                content()
            }
        }
    }
}