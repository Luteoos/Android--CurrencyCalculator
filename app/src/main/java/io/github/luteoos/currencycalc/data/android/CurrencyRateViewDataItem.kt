package io.github.luteoos.currencycalc.data.android

import android.icu.util.Currency

data class CurrencyRateViewDataItem(val currency : Currency,
                                    val value : Double,
                                    val isBase : Boolean = false) {
}