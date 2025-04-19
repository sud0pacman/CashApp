package com.sudo_pacman.cashapp.ui.viewmodel.add_card_view_model

sealed class AddCardEvent {
    object Back : AddCardEvent()
    data class AddCard(val cardNumber: String, val expireDate: String) : AddCardEvent()
    data class PhoneNumber(val phoneNumber: String) : AddCardEvent()
}