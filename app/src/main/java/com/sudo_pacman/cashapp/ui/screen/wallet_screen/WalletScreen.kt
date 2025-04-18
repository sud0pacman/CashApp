package com.sudo_pacman.cashapp.ui.screen.wallet_screen

import MultiShadowBox
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.sudo_pacman.cashapp.core.utils.VerticalSpace
import com.sudo_pacman.cashapp.core.utils.formatWithCommasAndDecimals
import com.sudo_pacman.cashapp.ui.screen.wallet_screen.components.IdentificationRequiredLayout
import com.sudo_pacman.cashapp.ui.screen.wallet_screen.components.PromoCodeBottomSheetContent
import com.sudo_pacman.cashapp.ui.screen.wallet_screen.components.WalletBalanceLayout
import com.sudo_pacman.cashapp.ui.screen.wallet_screen.components.WalletSimpleItemContent
import com.sudo_pacman.cashapp.ui.screen.wallet_screen.components.WalletSwitchesItemContent
import com.sudo_pacman.cashapp.ui.theme.*
import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletEvent
import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletState
import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun WalletScreen(viewModel: WalletViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    WalletScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalletScreenContent(
    state: WalletState,
    onEvent: (WalletEvent) -> Unit
) {
    var promocode by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expireDate by remember { mutableStateOf("") }

    var cashIsChecked by remember { mutableStateOf(true) }
    var cardIsChecked by remember { mutableStateOf(false) }

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = Color(0xffF4F4F4),
            dragHandle = {},
            onDismissRequest = { showBottomSheet = false }
        ) {
            PromoCodeBottomSheetContent(
                onValueChange = { promoTextFieldValue ->

                },
                onClickBack = { showBottomSheet = false },
                modifier = Modifier
                    .fillMaxWidth()
//                    .height(200.dp)
                    .padding(20.dp)
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Wallet") }
            )
        }
    ) { innerPadding ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (state.isLoading) {
                item {
                    CircularProgressIndicator()
                }
            }

            state.error?.let {
                item {
                    Text(text = "Error: $it", color = MaterialTheme.colorScheme.error)
                }
            }

            state.balance?.let {
                item {
                    WalletBalanceLayout(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        balance = it.formatWithCommasAndDecimals(),
                        onClick = {},
                    )
                }
            }

            item {
                22.VerticalSpace()
            }

            item {
                IdentificationRequiredLayout()
            }

            item {
                20.VerticalSpace()
            }

            item {
                MultiShadowBox(
                    content = {
                        WalletSimpleItemContent(imgSource = promocodeImg, title = "Add Promocode code")
                    },
                    onTap = {
                        showBottomSheet = true
                    }
                )
            }

            item {
                16.VerticalSpace()
            }

            item {
                MultiShadowBox(
                    content = {
                        WalletSwitchesItemContent(
                            imgSource = cashImg,
                            title = "Cash",
                            onCheckedChange = {
                                cashIsChecked = !cashIsChecked
                            },
                            isChecked = cashIsChecked
                        )
                    },
                    onTap = {}
                )
            }

            item {
                16.VerticalSpace()
            }

            item {
                MultiShadowBox(
                    content = {
                        WalletSwitchesItemContent(
                            imgSource = cardImg,
                            title = "Card **** 7777",
                            onCheckedChange = {
                                cardIsChecked = !cardIsChecked
                            },
                            isChecked = cardIsChecked
                        )
                    },
                    onTap = {}
                )
            }

            item {
                16.VerticalSpace()
            }

            item {
                MultiShadowBox(
                    content = {
                        WalletSimpleItemContent(
                            imgSource = addCardImg,
                            title = "Add new card",
                        )
                    },
                    onTap = {}
                )
            }
        }
    }
}
