package com.sudo_pacman.cashapp.ui.screen.wallet_screen

import MultiShadowBox
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sudo_pacman.cashapp.core.utils.VerticalSpace
import com.sudo_pacman.cashapp.core.utils.formatWithCommasAndDecimals
import com.sudo_pacman.cashapp.ui.navigation.Routes
import com.sudo_pacman.cashapp.ui.screen.add_card_screen.components.AnimatedSettingsList
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
fun WalletScreen(navController: NavController) {
    val viewModel: WalletViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigationEvents.collect { event ->
            when (event) {
                is WalletViewModel.NavigationEvent.NavigateToAddCard -> {
                    navController.navigate(Routes.AddCard+"/${state.phone}")
                }
            }
        }
    }

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
    var cashIsChecked by remember { mutableStateOf(state.activeMethod == "cash") }
    var cardIsChecked by remember { mutableStateOf(state.activeCardId != null) }

    var showBottomSheet by remember { mutableStateOf(false) }

    if (showBottomSheet) {
        ModalBottomSheet(
            containerColor = Color(0xffF4F4F4),
            dragHandle = {},
            onDismissRequest = { showBottomSheet = false }
        ) {
            PromoCodeBottomSheetContent(
                onClickSave = {
                    onEvent(WalletEvent.ActivatePromocode(it))
                },
                onClickBack = { showBottomSheet = false },
                modifier = Modifier
                    .fillMaxWidth()
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
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            AnimatedSettingsList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                items = listOf(
                    {
                        state.error?.let {
                            Text(
                                text = "Error: $it",
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    },
                    {
                        state.balance.run {
                            WalletBalanceLayout(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp),
                                balance = this?.formatWithCommasAndDecimals() ?: 0.0.formatWithCommasAndDecimals(),
                                onClick = {},
                            )
                        }
                    },
                    { 22.VerticalSpace() },
                    { IdentificationRequiredLayout() },
                    { 20.VerticalSpace() },
                    {
                        MultiShadowBox(
                            content = {
                                WalletSimpleItemContent(
                                    imgSource = promocodeImg,
                                    title = "Add Promocode code"
                                )
                            },
                            onTap = {
                                showBottomSheet = true
                            }
                        )
                    },
                    { 16.VerticalSpace() },
                    {
                        MultiShadowBox(
                            content = {
                                WalletSwitchesItemContent(
                                    imgSource = cashImg,
                                    title = "Cash",
                                    onCheckedChange = {
                                        cashIsChecked = !cashIsChecked
                                        if (cashIsChecked) onEvent(WalletEvent.UpdatePaymentMethod(method = "cash"))
                                        else onEvent(WalletEvent.UpdatePaymentMethod(method = "card"))
                                    },
                                    isChecked = cashIsChecked
                                )
                            },
                            onTap = {}
                        )
                    },
                    { 16.VerticalSpace() },
                    {
                        MultiShadowBox(
                            content = {
                                WalletSwitchesItemContent(
                                    imgSource = cardImg,
                                    title = "Card **** ${if (state.cards.isNotEmpty()) state.cards.first().number.substring(12) else "7777"}",
                                    onCheckedChange = {
                                        cardIsChecked = !cardIsChecked
                                        if (!state.isLoading) {
                                            if (cardIsChecked) onEvent(WalletEvent.UpdatePaymentMethod(method = "card", cardId = state.cards.first().id))
                                            else onEvent(WalletEvent.UpdatePaymentMethod(method = "cash"))
                                        }
                                    },
                                    isChecked = cardIsChecked
                                )
                            },
                            onTap = {}
                        )
                    },
                    { 16.VerticalSpace() },
                    {
                        MultiShadowBox(
                            content = {
                                WalletSimpleItemContent(
                                    imgSource = addCardImg,
                                    title = "Add new card"
                                )
                            },
                            onTap = {
                                onEvent(WalletEvent.ClickAddNewCard)
                            }
                        )
                    }
                )
            )


            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}
