package com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model

sealed class AddCardEvent {
    object Back : AddCardEvent()
    data class AddCard(val phoneNumber: String, val cardNumber: String, val expireDate: String) : AddCardEvent()
}