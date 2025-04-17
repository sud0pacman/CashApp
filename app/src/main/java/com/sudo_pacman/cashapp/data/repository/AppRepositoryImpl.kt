package com.sudo_pacman.cashapp.data.repository

import com.sudo_pacman.cashapp.data.model.*
import com.sudo_pacman.cashapp.data.remote.ApiService
import com.sudo_pacman.cashapp.domain.repository.AppRepository

class AppRepositoryImpl(private val apiService: ApiService) : AppRepository {
    override suspend fun createUser(phone: String): Result<Unit> {
        return apiService.createUser(phone)
    }

    override suspend fun getWallet(phone: String): Result<WalletResponse> {
        return apiService.getWallet(phone)
    }

    override suspend fun getCards(phone: String): Result<List<CardResponse>> {
        return apiService.getCards(phone)
    }

    override suspend fun updatePaymentMethod(phone: String, request: UpdateMethodRequest): Result<Unit> {
        return apiService.updatePaymentMethod(phone, request)
    }

    override suspend fun activatePromocode(phone: String, code: String): Result<Unit> {
        return apiService.activatePromocode(phone, code)
    }

    override suspend fun addCard(phone: String, request: AddCardRequest): Result<Unit> {
        return apiService.addCard(phone, request)
    }
}