package com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model

sealed class WalletEvent {
    object LoadData : WalletEvent()
    object ClickAddNewCard : WalletEvent()
    data class UpdatePaymentMethod(val method: String, val cardId: Int? = null) : WalletEvent()
    data class ActivatePromocode(val code: String) : WalletEvent()
}

