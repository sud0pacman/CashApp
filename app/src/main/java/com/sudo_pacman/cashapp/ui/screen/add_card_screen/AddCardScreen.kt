package com.sudo_pacman.cashapp.ui.screen.add_card_screen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sudo_pacman.cashapp.core.utils.VerticalSpace
import com.sudo_pacman.cashapp.core.utils.clearCardNumber
import com.sudo_pacman.cashapp.ui.main_components.CircleShadowComponent
import com.sudo_pacman.cashapp.ui.main_components.PrimaryButton
import com.sudo_pacman.cashapp.ui.screen.add_card_screen.components.AddCardItem
import com.sudo_pacman.cashapp.ui.theme.arrowLeftImg
import com.sudo_pacman.cashapp.ui.theme.medium16
import com.sudo_pacman.cashapp.ui.theme.semiBold25
import com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model.AddCardEvent
import com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model.AddCardEvent.AddCard
import com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model.AddCardState
import com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model.AddCardViewModel
import com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model.AddCardViewModel.NavigationEvent
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(viewModel: AddCardViewModel = koinViewModel(), navController: NavController) {
    val state by viewModel.state.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(state.message) {
        Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
    }

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is NavigationEvent.Back -> {
                    navController.popBackStack()
                }
            }
        }
    }

    AddCardScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreenContent(
    state: AddCardState,
    onEvent: (AddCardEvent) -> Unit
) {
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var inputPhase by remember { mutableStateOf(InputPhase.CARD_NUMBER) }

    val maxCardLength = 19 // 16 raqam + 3 ta bo'sh joy (#### #### #### ####)
    val maxExpiryLength = 5 // MM/YY


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    CircleShadowComponent(
                        modifier = Modifier.padding(start = 12.dp),
                        size = 44.dp,
                        onClick = {
                            onEvent(AddCardEvent.Back)
                        },
                        content = {
                            Image(
                                painter = painterResource(arrowLeftImg),
                                contentDescription = "back"
                            )
                        }
                    )
                },
                title = {
                    Text(
                        "Add Card",
                        style = semiBold25,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddCardItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                content = {
                    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
                        OutlinedTextField(
                            value = cardNumber,
                            onValueChange = { input ->
                                val digits = input.filter { it.isDigit() }
                                val formatted = digits.chunked(4).joinToString(" ")
                                if (formatted.length <= maxCardLength) {
                                    cardNumber = formatted
                                }
                                if (digits.length == 16) {
                                    inputPhase = InputPhase.EXPIRY_DATE
                                }
                            },
                            placeholder = {
                                Text("0000 0000 0000 0000", style = medium16.copy(color = Color(0xffA1A5B7)))
                            },
                            readOnly = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0x8E8E8E4D).copy(alpha = 0.30f),
                                unfocusedBorderColor = Color(0x8E8E8E4D).copy(alpha = 0.30f),
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // Amal qilish muddati
                        OutlinedTextField(
                            value = expiryDate,
                            onValueChange = { input ->
                                val digits = input.filter { it.isDigit() }
                                val formatted = when {
                                    digits.length <= 2 -> digits
                                    digits.length <= 4 -> digits.substring(0, 2) + "/" + digits.substring(2)
                                    else -> digits.substring(0, 2) + "/" + digits.substring(2, 4)
                                }
                                if (formatted.length <= maxExpiryLength) {
                                    expiryDate = formatted
                                }
                            },
                            placeholder = {
                                Text("oo/yy", style = medium16.copy(color = Color(0xffA1A5B7)))
                            },
                            readOnly = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0x8E8E8E4D).copy(alpha = 0.30f),
                                unfocusedBorderColor = Color(0x8E8E8E4D).copy(alpha = 0.30f),
                            ),
                            modifier = Modifier.width(90.dp),
                        )

                    }
                }
            )

            70.VerticalSpace()

            if (state.isLoading == false) PrimaryButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    onClick = {
//                        cardNumber = "1234123412341234"
//                        expiryDate = "12/25"
                        Log.d("AddCard", "${clearCardNumber(cardNumber)} ==> $expiryDate")
                        onEvent.invoke(AddCard(phoneNumber = "+998901234567", cardNumber = clearCardNumber(cardNumber), expireDate = expiryDate))
                    },
                    enabled = cardNumber.length == maxCardLength && expiryDate.length == maxExpiryLength
                ) {
                    Text(
                        "Save",
                        style = medium16
                    )
                }
            else {
                CircularProgressIndicator()
            }

            20.VerticalSpace()

            // Custom number pad
            NumberPad(
                onNumberClick = { digit ->
                    when (inputPhase) {
                        InputPhase.CARD_NUMBER -> {
                            val unformatted = cardNumber.filter { it.isDigit() }
                            if (unformatted.length < 16) {
                                val newInput = unformatted + digit
                                val formatted = newInput.chunked(4).joinToString(" ")
                                cardNumber = formatted
                                if (newInput.length == 16) inputPhase = InputPhase.EXPIRY_DATE
                            }
                        }

                        InputPhase.EXPIRY_DATE -> {
                            val unformatted = expiryDate.filter { it.isDigit() }
                            if (unformatted.length < 4) {
                                val newInput = unformatted + digit
                                val formatted = when {
                                    newInput.length <= 2 -> newInput
                                    newInput.length <= 4 -> newInput.substring(0, 2) + "/" + newInput.substring(2)
                                    else -> newInput.substring(0, 2) + "/" + newInput.substring(2, 4)
                                }
                                expiryDate = formatted
                            }
                        }
                    }
                },
                onDeleteClick = {
                    when (inputPhase) {
                        InputPhase.EXPIRY_DATE -> {
                            val digits = expiryDate.filter { it.isDigit() }
                            if (digits.isNotEmpty()) {
                                val newInput = digits.dropLast(1)
                                val formatted = when {
                                    newInput.length <= 2 -> newInput
                                    else -> newInput.substring(0, 2) + "/" + newInput.substring(2)
                                }
                                expiryDate = formatted
                            } else {
                                inputPhase = InputPhase.CARD_NUMBER
                            }
                        }

                        InputPhase.CARD_NUMBER -> {
                            val digits = cardNumber.filter { it.isDigit() }
                            if (digits.isNotEmpty()) {
                                val newInput = digits.dropLast(1)
                                cardNumber = newInput.chunked(4).joinToString(" ")
                            }
                        }
                    }
                }
            )
        }
    }
}

enum class InputPhase {
    CARD_NUMBER, EXPIRY_DATE
}

@Composable
fun NumberPad(
    onNumberClick: (String) -> Unit,
    onDeleteClick: () -> Unit
) {
    val keys = listOf(
        listOf("1", "2", "3"),
        listOf("4", "5", "6"),
        listOf("7", "8", "9"),
        listOf("", "0", "⌫")
    )

    Column {
        keys.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                row.forEach { key ->
                    val interactionSource = remember { MutableInteractionSource() }
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .clickable(
                                indication = LocalIndication.current,
                                interactionSource = interactionSource,
                            ) {
                                when (key) {
                                    "⌫" -> onDeleteClick()
                                    "" -> {}
                                    else -> onNumberClick(key)
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = key, style = MaterialTheme.typography.headlineSmall)
                    }
                }
            }
        }
    }
}
