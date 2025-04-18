package com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sudo_pacman.cashapp.data.model.AddCardRequest
import com.sudo_pacman.cashapp.domain.repository.AppRepository
import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletViewModel
import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletViewModel.NavigationEvent
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddCardViewModel(private val repository: AppRepository) : ViewModel() {
    private val _state = MutableStateFlow(AddCardState())
    val state: StateFlow<AddCardState> = _state.asStateFlow()
    private val _navigationEvents = Channel<NavigationEvent>()
    val navigationEvents = _navigationEvents.receiveAsFlow()


    fun onEvent(event: AddCardEvent) {
        when (event) {
            is AddCardEvent.AddCard -> {
                viewModelScope.launch {
                    viewModelScope.launch {
                        _state.update { it.copy(isLoading = true, error = null) }
                        val request = AddCardRequest(event.cardNumber, event.expireDate)
                        repository.addCard(event.phoneNumber, request).fold(
                            onSuccess = {
                                Log.d("AddCard", "success ${it}")
                                _state.update { it.copy(isLoading = false, message = "Successfully added") }
                                _navigationEvents.send(AddCardViewModel.NavigationEvent.Back)
                            },
                            onFailure = { e ->
                                Log.d("AddCard", "fail ${e.message}")
                                _state.update { it.copy(error = e.message, isLoading = false) }
                            }
                        )
                    }
                }
            }
            is AddCardEvent.Back -> {
                viewModelScope.launch {
                    _navigationEvents.send(NavigationEvent.Back)
                }
            }
        }
    }


    sealed class NavigationEvent {
        object Back : NavigationEvent()
    }
}