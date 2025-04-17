package com.sudo_pacman.cashapp.di

import com.sudo_pacman.cashapp.data.remote.ApiService
import com.sudo_pacman.cashapp.data.repository.AppRepositoryImpl
import com.sudo_pacman.cashapp.domain.repository.AppRepository
import com.sudo_pacman.cashapp.ui.viewmodel.wallat_view_model.WalletViewModel
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        HttpClient(Android) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
    single { ApiService(get()) }
    single<AppRepository> { AppRepositoryImpl(get()) }
    viewModel { WalletViewModel(get()) }
}