package com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model

import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletEvent

sealed class AddCardEvent {
    data class AddCard(val number: String, val expireDate: String) : AddCardEvent()
}