package io.github.luteoos.currencycalc.di

import io.github.luteoos.currencycalc.utils.Session
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

/**
 * Add your DI here
 */
val appModule = module {
    single { Session(androidContext()) }

}

/**
 * Add your viewModel DI here ex. viewModel { TemplateViewModel() }
 */
val vmModule = module {

}

val koinModules = listOf(appModule, vmModule)