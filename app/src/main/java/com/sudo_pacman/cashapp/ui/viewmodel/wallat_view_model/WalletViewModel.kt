package com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.cashapp.data.model.AddCardRequest
import com.sudo_pacman.cashapp.data.model.UpdateMethodRequest
import com.sudo_pacman.cashapp.domain.repository.AppRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WalletViewModel(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(WalletState())
    val state: StateFlow<WalletState> = _state.asStateFlow()

    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()

    init {
        createUser()
        onEvent(WalletEvent.LoadData)
        onEvent(WalletEvent.UpdatePaymentMethod("cash"))
    }

    private fun createUser() {
        viewModelScope.launch {
            repository.createUser(_state.value.phone)
        }
    }

    fun onEvent(event: WalletEvent) {
        when (event) {
            is WalletEvent.LoadData -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }

                    val walletResult = repository.getWallet(_state.value.phone)
                    val cardsResult = repository.getCards(_state.value.phone)

                    walletResult.fold(
                        onSuccess = { wallet ->
                            _state.update {
                                it.copy(
                                    balance = wallet.balance,
                                    activeMethod = wallet.active_method,
                                    activeCardId = wallet.active_card_id
                                )
                            }
                        },
                        onFailure = { e ->
                            _state.update { it.copy(error = e.message) }
                        }
                    )

                    cardsResult.fold(
                        onSuccess = { cards ->
                            _state.update { it.copy(cards = cards) }
                        },
                        onFailure = { e ->
                            _state.update { it.copy(error = e.message) }
                        }
                    )

                    _state.update { it.copy(isLoading = false) }
                }
            }
            is WalletEvent.UpdatePaymentMethod -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    val request = UpdateMethodRequest(event.method, event.cardId)
                    repository.updatePaymentMethod(_state.value.phone, request).fold(
                        onSuccess = {
                            _state.update {
                                it.copy(
                                    activeMethod = event.method,
                                    activeCardId = event.cardId,
                                    isLoading = false
                                )
                            }
                            onEvent(WalletEvent.LoadData)
                        },
                        onFailure = { e ->
                            _state.update { it.copy(error = e.message, isLoading = false) }
                        }
                    )
                }
            }
            is WalletEvent.ActivatePromocode -> {
                viewModelScope.launch {
                    _state.update { it.copy(isLoading = true, error = null) }
                    repository.activatePromocode(_state.value.phone, event.code).fold(
                        onSuccess = {
                            _state.update { it.copy(promocodeSuccess = true, isLoading = false) }
                            onEvent(WalletEvent.LoadData) // Balansni yangilash
                        },
                        onFailure = { e ->
                            _state.update { it.copy(error = e.message, isLoading = false) }
                        }
                    )
                }
            }
            is WalletEvent.ClickAddNewCard -> {
                viewModelScope.launch {
                    _navigationEvents.send(NavigationEvent.NavigateToAddCard)
                }
            }
        }
    }


    sealed class NavigationEvent {
        object NavigateToAddCard : NavigationEvent()
    }
}