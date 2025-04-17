package com.sudo_pacman.cashapp.domain.repository

import com.sudo_pacman.cashapp.data.model.*

interface AppRepository {
    suspend fun createUser(phone: String): Result<Unit>
    suspend fun getWallet(phone: String): Result<WalletResponse>
    suspend fun getCards(phone: String): Result<List<CardResponse>>
    suspend fun updatePaymentMethod(phone: String, request: UpdateMethodRequest): Result<Unit>
    suspend fun activatePromocode(phone: String, code: String): Result<Unit>
    suspend fun addCard(phone: String, request: AddCardRequest): Result<Unit>
}