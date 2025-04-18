package com.sudo_pacman.cashapp.ui.screen.add_card_screen.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestTextField() {
    val interactionSource = remember { MutableInteractionSource() }
    var textFieldValue = remember { "" }

    BasicTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValue = it
        },
        modifier = Modifier,
//        visualTransformation = visualTransformation,
        interactionSource = interactionSource,
        enabled = true,
        singleLine = true,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = textFieldValue,
            visualTransformation = VisualTransformation.None,
            innerTextField = innerTextField,
            singleLine = true,
            enabled = true,
            interactionSource = interactionSource,
            contentPadding = PaddingValues(0.dp), // this is how you can remove the padding
        )
    }
}