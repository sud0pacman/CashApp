package com.sudo_pacman.cashapp.ui.screen.wallet_screen.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sudo_pacman.cashapp.core.utils.HorizontalSpace
import com.sudo_pacman.cashapp.core.utils.VerticalSpace
import com.sudo_pacman.cashapp.ui.main_components.CircleShadowComponent
import com.sudo_pacman.cashapp.ui.main_components.PrimaryButton
import com.sudo_pacman.cashapp.ui.theme.arrowLeftImg
import com.sudo_pacman.cashapp.ui.theme.medium16
import com.sudo_pacman.cashapp.ui.theme.medium18

@Composable
fun PromoCodeBottomSheetContent(
    modifier: Modifier = Modifier,
    onClickBack: () -> Unit,
    onValueChange: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    val focusRequester = remember { FocusRequester() }

    // Auto focus when this Composable is shown
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleShadowComponent(
                onClick = onClickBack,
                content = {
                    Image(
                        painter = painterResource(arrowLeftImg),
                        contentDescription = null,
                    )
                }
            )

            16.HorizontalSpace()

            Text(
                "Enter Promo code",
                style = medium18
            )
        }

        32.VerticalSpace()

        TextField(
            value = text,
            onValueChange = {
                text = it
                onValueChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                disabledIndicatorColor = Color.Gray,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent
            ),
            singleLine = true,
        )

        16.VerticalSpace()

        PrimaryButton(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            content = {
                Text(
                    "Save",
                    color = Color.White,
                    style = medium16.copy(color = Color.White)
                )
            }
        )
    }
}