package com.sudo_pacman.cashapp.ui.screen.wallet_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sudo_pacman.cashapp.R
import com.sudo_pacman.cashapp.core.utils.HorizontalSpace
import com.sudo_pacman.cashapp.ui.theme.blackBorderColor
import com.sudo_pacman.cashapp.ui.theme.semiBold14

@Composable
fun IdentificationRequiredLayout(
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .border(width = 1.dp, color = blackBorderColor, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(height = 60.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClick = {}
            )
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.info_circle),
                contentDescription = "Info",
                modifier = Modifier
                    .height(height = 24.dp)
                    .width(width = 24.dp)
            )

            8.HorizontalSpace()

            Text(
                "Identification required",
                style = semiBold14,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.arrow_up_right),
                contentDescription = null
            )
        }
    }
}