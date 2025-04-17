package com.sudo_pacman.cashapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sudo_pacman.cashapp.ui.viewmodel.WalletEvent
import com.sudo_pacman.cashapp.ui.viewmodel.WalletState
import com.sudo_pacman.cashapp.ui.viewmodel.WalletViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WalletScreen(viewModel: WalletViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    WalletScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@Composable
fun WalletScreenContent(
    state: WalletState,
    onEvent: (WalletEvent) -> Unit
) {
    var promocode by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expireDate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (state.isLoading) {
            CircularProgressIndicator()
        }

        state.error?.let {
            Text(text = "Xatolik: $it", color = MaterialTheme.colorScheme.error)
        }

        state.balance?.let {
            Text(
                text = "Balans: $it UZS",
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        // To'lov usuli tanlash
        Text("To'lov usuli:", fontSize = 18.sp)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { onEvent(WalletEvent.UpdatePaymentMethod("cash")) },
                enabled = state.activeMethod != "cash"
            ) {
                Text("Naqd")
            }
            Button(
                onClick = {
                    state.cards.firstOrNull()?.let {
                        onEvent(WalletEvent.UpdatePaymentMethod("card", it.id))
                    }
                },
                enabled = state.activeMethod != "card" && state.cards.isNotEmpty()
            ) {
                Text("Karta")
            }
        }

        // Kartalar ro'yxati
        Text("Kartalar:", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp))
        LazyColumn {
            items(state.cards) { card ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("**** ${card.number.takeLast(4)}")
                        Text("Muddat: ${card.expire_date}")
                        if (state.activeMethod == "card" && state.activeCardId == card.id) {
                            Text("Faol", color = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        }

        // Promokod
        OutlinedTextField(
            value = promocode,
            onValueChange = { promocode = it },
            label = { Text("Promokod") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        )
        Button(
            onClick = { onEvent(WalletEvent.ActivatePromocode(promocode)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Promokodni faollashtirish")
        }

        if (state.promocodeSuccess) {
            Text(
                text = "Promokod muvaffaqiyatli faollashtirildi!",
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Yangi karta qo'shish
        Text("Yangi karta qo'shish:", fontSize = 18.sp, modifier = Modifier.padding(top = 16.dp))
        OutlinedTextField(
            value = cardNumber,
            onValueChange = { cardNumber = it },
            label = { Text("Karta raqami") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = expireDate,
            onValueChange = { expireDate = it },
            label = { Text("Amal qilish muddati (MM/YY)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        Button(
            onClick = { onEvent(WalletEvent.AddCard(cardNumber, expireDate)) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Karta qo'shish")
        }
    }
}