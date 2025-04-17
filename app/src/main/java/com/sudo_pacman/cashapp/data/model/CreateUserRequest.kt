package com.sudo_pacman.cashapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CreateUserRequest(val phone: String)

@Serializable
data class WalletResponse(
    val balance: Double,
    val active_method: String,
    val active_card_id: Int? = null
)

@Serializable
data class CardResponse(
    val id: Int,
    val number: String,
    val expire_date: String
)

@Serializable
data class UpdateMethodRequest(
    val active_method: String,
    val active_card_id: Int? = null
)

@Serializable
data class PromocodeRequest(val code: String)

@Serializable
data class AddCardRequest(
    val number: String,
    val expire_date: String
)

@Serializable
data class ErrorResponse(val message: String)