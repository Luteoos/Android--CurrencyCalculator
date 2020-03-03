package io.github.luteoos.currencycalc.data.android

import android.icu.util.Currency

data class CurrencyRateViewDataItem(val currency : String,
                                    val value : Double,
                                    val isBase : Boolean = false) {
    fun getCurrency(): Currency = Currency.getInstance(currency)
}