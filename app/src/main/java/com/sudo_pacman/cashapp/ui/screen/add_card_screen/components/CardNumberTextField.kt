package com.sudo_pacman.cashapp.ui.screen.add_card_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudo_pacman.cashapp.ui.theme.medium16
import java.time.format.TextStyle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = value,
        onValueChange = {
            // Avto-formatlash: 4 xonadan keyin bo'sh joy qo'yish
            val digitsOnly = it.filter { char -> char.isDigit() }.take(16)
            val spaced = digitsOnly.chunked(4).joinToString(" ")
            onValueChange(spaced)
        },
        interactionSource = interactionSource,
        singleLine = true,
        textStyle = medium16,
        modifier = modifier
            .height(36.dp)
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(16.dp))
            .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = value,
            innerTextField = innerTextField,
            enabled = true,
            singleLine = true,
            visualTransformation = VisualTransformation.None,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(0.dp),
        )
    }
}
