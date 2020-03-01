package io.github.luteoos.currencycalc.data.android

data class CurrencyRateViewDataItem(val currency : String,
                                    val value : Double,
                                    val isBase : Boolean = false) {
}