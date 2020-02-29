package io.github.luteoos.currencycalc.di

import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

class DITest : KoinTest {

    @Test
    fun checkDependencyGraph(){
        koinApplication {
            modules(koinModules)
        }.checkModules()
    }
}