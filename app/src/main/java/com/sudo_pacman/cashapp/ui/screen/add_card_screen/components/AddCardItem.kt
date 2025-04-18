package com.sudo_pacman.cashapp.ui.screen.add_card_screen.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddCardItem(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {

    Box(
       modifier = modifier
           .shadow(ambientColor = Color.White, spotColor = Color.Black.copy(alpha = .8f), elevation = 6.dp, shape = RoundedCornerShape(16.dp))
           .border(width = 2.dp, color = Color.White, shape = RoundedCornerShape(18.dp))
           .background(Color(0xffF7F8FC))
    ) {
        content()
    }
}