package io.github.luteoos.currencycalc.di

import io.github.luteoos.currencycalc.network.GsonProvider
import io.github.luteoos.currencycalc.network.OkHttpClientProvider
import io.github.luteoos.currencycalc.network.RestService
import io.github.luteoos.currencycalc.repository.CurrencyRatesRepository
import io.github.luteoos.currencycalc.viewmodel.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Add your DI here
 */
val appModule = module {
    single { GsonProvider() }
    single { OkHttpClientProvider() }
    single { RestService(get(), get()) }
    single { CurrencyRatesRepository(get()) }
}

/**
 * Add your viewModel DI here ex. viewModel { TemplateViewModel() }
 */
val vmModule = module {
    viewModel { MainScreenViewModel(get()) }
}

val koinModules = listOf(appModule, vmModule)