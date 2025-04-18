package com.sudo_pacman.cashapp.ui.screen.wallet_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sudo_pacman.cashapp.core.utils.HorizontalSpace
import com.sudo_pacman.cashapp.ui.theme.medium16

@Composable
fun WalletSwitchesItemContent(
    imgSource: Int,
    title: String,
    isChecked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)?,
) {
    Row {
        Image(
            painter = painterResource(imgSource),
            contentDescription = null,
        )

        12.HorizontalSpace()

        Text(
            title,
            style = medium16
        )

        Spacer(modifier = Modifier.weight(1f))

        Switch(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            thumbContent = {
                Box(
                    contentAlignment = Alignment.Center,
                    content = {
                        // Bottom dark shadow 1
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(22.dp)
                                .offset(y = 1.dp)
                                .blur(
                                    radius = 2.dp,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                )
                                .background(
                                    color = Color(0x1018280F).copy(alpha = 0.06f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        )

                        // Bottom dark shadow 2
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(22.dp)
                                .offset(y = 1.dp)
                                .blur(
                                    radius = 3.dp,
                                    edgeTreatment = BlurredEdgeTreatment.Unbounded
                                )
                                .background(
                                    color = Color(0x1018280F).copy(alpha = 0.10f),
                                    shape = CircleShape
                                )
                        )


                        // Actual button with gradient and bevel border
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .size(22.dp)
                                .background(
                                    color = Color.White,
                                    shape = CircleShape
                                )
                        )
                    }
                )
            },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Transparent,
                checkedTrackColor = Color.Black,
                checkedBorderColor = Color.Black,
                uncheckedThumbColor = Color.Transparent,
                uncheckedTrackColor = Color(0xffECEDF1),
                uncheckedBorderColor = Color(0xffECEDF1),
                disabledCheckedThumbColor = Color.Transparent,
                disabledCheckedTrackColor = Color.Transparent,
                disabledCheckedBorderColor = Color.Transparent,
                disabledUncheckedThumbColor = Color.Transparent,
                disabledUncheckedTrackColor = Color.Transparent,
                disabledUncheckedBorderColor = Color.Transparent,
            ),
            modifier = Modifier
                .width(44.dp)
                .height(22.dp),
        )
    }
}