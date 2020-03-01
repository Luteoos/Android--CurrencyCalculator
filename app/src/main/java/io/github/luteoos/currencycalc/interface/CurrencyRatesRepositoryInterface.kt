package io.github.luteoos.currencycalc.`interface`

import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataWrapper
import io.reactivex.rxjava3.core.Flowable

interface CurrencyRatesRepositoryInterface {
    fun getCurrencyRates(currency: String) : Flowable<CurrencyRatesDataWrapper>
}