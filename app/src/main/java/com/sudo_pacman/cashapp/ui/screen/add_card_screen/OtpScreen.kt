package com.sudo_pacman.cashapp.ui.screen.add_card_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpScreen() {
    // 4 xonali OTP uchun
    val otpCode = remember { mutableStateListOf("", "", "", "") }
    val otpFocusRequesters = List(4) { remember { FocusRequester() } }

    // Tagidagi 3 xonali OTP uchun
    val otherOtp = remember { mutableStateListOf("", "", "") }
    val otherFocusRequesters = List(3) { remember { FocusRequester() } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        Text("Enter the 4-digit code", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(24.dp))

        // 4 xonali OTP inputlar
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            otpCode.forEachIndexed { index, value ->
                OtpBox(
                    value = value,
                    onValueChange = {
                        if (it.length <= 1) otpCode[index] = it
                        if (it.length == 1 && index < 3) {
                            otpFocusRequesters[index + 1].requestFocus()
                        }
                    },
                    focusRequester = otpFocusRequesters[index]
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Other OTP Inputs", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(16.dp))

        // Pastdagi 3ta OTP input
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            otherOtp.forEachIndexed { index, value ->
                OtpBox(
                    value = value,
                    onValueChange = {
                        if (it.length <= 1) otherOtp[index] = it
                        if (it.length == 1 && index < 2) {
                            otherFocusRequesters[index + 1].requestFocus()
                        }
                    },
                    focusRequester = otherFocusRequesters[index]
                )
            }
        }
    }
}

@Composable
fun OtpBox(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .size(60.dp)
            .focusRequester(focusRequester)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .background(Color.White)
            .focusable()
            .clickable { focusRequester.requestFocus() },
        singleLine = true,
        textStyle = LocalTextStyle.current.copy(
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}


@Composable
fun NumberPad(
    onNumberClick: (String) -> Unit,
    onBackspaceClick: () -> Unit
) {
    val numbers = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "<")
    )

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        numbers.forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                row.forEach { label ->
                    if (label.isEmpty()) {
                        Spacer(modifier = Modifier.size(60.dp))
                    } else {
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable {
                                    if (label == "<") onBackspaceClick()
                                    else onNumberClick(label)
                                }
                                .background(Color.LightGray),
                            contentAlignment = Alignment.Center
                        ) {
                            if (label == "<") {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Backspace"
                                )
                            } else {
                                Text(label, style = MaterialTheme.typography.headlineSmall)
                            }
                        }
                    }
                }
            }
        }
    }
}
