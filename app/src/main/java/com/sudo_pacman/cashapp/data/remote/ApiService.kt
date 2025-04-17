package com.sudo_pacman.cashapp.data.remote

import com.sudo_pacman.cashapp.data.model.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

class ApiService(private val client: HttpClient) {
    private val baseUrl = "https://wedrive-assignment-api.onrender.com"

    suspend fun createUser(phone: String): Result<Unit> {
        return try {
            client.post("$baseUrl/users") {
                contentType(ContentType.Application.Json)
                setBody(CreateUserRequest(phone))
            }.body<Unit>()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getWallet(phone: String): Result<WalletResponse> {
        return try {
            val response = client.get("$baseUrl/wallet") {
                header("X-Account-Phone", phone)
            }.body<WalletResponse>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getCards(phone: String): Result<List<CardResponse>> {
        return try {
            val response = client.get("$baseUrl/cards") {
                header("X-Account-Phone", phone)
            }.body<List<CardResponse>>()
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updatePaymentMethod(phone: String, request: UpdateMethodRequest): Result<Unit> {
        return try {
            client.put("$baseUrl/wallet/method") {
                header("X-Account-Phone", phone)
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<Unit>()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun activatePromocode(phone: String, code: String): Result<Unit> {
        return try {
            client.post("$baseUrl/promocode") {
                header("X-Account-Phone", phone)
                contentType(ContentType.Application.Json)
                setBody(PromocodeRequest(code))
            }.body<Unit>()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun addCard(phone: String, request: AddCardRequest): Result<Unit> {
        return try {
            client.post("$baseUrl/cards") {
                header("X-Account-Phone", phone)
                contentType(ContentType.Application.Json)
                setBody(request)
            }.body<Unit>()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}