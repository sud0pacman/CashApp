package com.sudo_pacman.cashapp.ui.screen.wallet_screen

import MultiShadowBox
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.TextObfuscationMode.Companion.Hidden
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.sudo_pacman.cashapp.core.utils.VerticalSpace
import com.sudo_pacman.cashapp.core.utils.formatWithCommasAndDecimals
import com.sudo_pacman.cashapp.ui.main_components.LoadingComponent
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

    val context = LocalContext.current

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
    var cardIsChecked by remember { mutableStateOf(state.activeMethod != "cash") }

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showBottomSheet) {
        ModalBottomSheet(
            sheetState = sheetState,
            containerColor = Color(0xffF4F4F4),
            dragHandle = {},
            onDismissRequest = { showBottomSheet = false }
        ) {
            PromoCodeBottomSheetContent(
                onClickSave = {
                    showBottomSheet = false
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
                        WalletBalanceLayout(
                            modifier = Modifier
                                .padding(bottom = 20.dp)
                                .fillMaxWidth()
                                .height(100.dp),
                            balance = state.balance?.formatWithCommasAndDecimals() ?: 0.0.formatWithCommasAndDecimals(),
                            onClick = {},
                        )
                    },
                    {
                        IdentificationRequiredLayout(modifier = Modifier.padding(bottom = 20.dp))
                    },
                    {
                        MultiShadowBox(
                            modifier = Modifier.padding(bottom = 16.dp),
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
                    {
                        MultiShadowBox(
                            modifier = Modifier.padding(bottom = 16.dp),
                            content = {
                                WalletSwitchesItemContent(
                                    imgSource = cashImg,
                                    title = "Cash",
                                    onCheckedChange = {
                                        cashIsChecked = !cashIsChecked
                                        if (cashIsChecked) onEvent(WalletEvent.UpdatePaymentMethod(method = "cash"))
                                        else onEvent(WalletEvent.UpdatePaymentMethod(method = "card", cardId = state.cards.first().id))
                                    },
                                    isChecked = cashIsChecked
                                )
                            },
                            onTap = {}
                        )
                    },
                    {
                        MultiShadowBox(
                            modifier = Modifier.padding(bottom = 16.dp),
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
                LoadingComponent()
            }
        }
    }
}
