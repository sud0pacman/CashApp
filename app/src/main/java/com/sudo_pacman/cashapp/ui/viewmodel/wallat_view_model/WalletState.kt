package com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model

import com.sudo_pacman.cashapp.data.model.*

data class WalletState(
    val isLoading: Boolean = false,
    val balance: Double? = null,
    val cards: List<CardResponse> = emptyList(),
    val activeMethod: String = "cash",
    val activeCardId: Int? = null,
    val error: String? = null,
    val promocodeSuccess: Boolean = false,
    val phone: String = "+998901234567" // Default telefon
)

sealed class WalletEvent {
    object LoadData : WalletEvent()
    data class UpdatePaymentMethod(val method: String, val cardId: Int? = null) : WalletEvent()
    data class ActivatePromocode(val code: String) : WalletEvent()
    data class AddCard(val number: String, val expireDate: String) : WalletEvent()
}

