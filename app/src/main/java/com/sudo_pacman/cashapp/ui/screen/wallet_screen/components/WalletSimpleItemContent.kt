package com.sudo_pacman.cashapp.ui.screen.wallet_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sudo_pacman.cashapp.R
import com.sudo_pacman.cashapp.core.utils.HorizontalSpace
import com.sudo_pacman.cashapp.ui.theme.medium16

@Composable
fun WalletSimpleItemContent(
    imgSource: Int,
    title: String,
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

        Image(
            painter = painterResource(R.drawable.right),
            contentDescription = "launch"
        )
    }
}