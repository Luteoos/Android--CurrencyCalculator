package io.github.luteoos.currencycalc.data.android

data class CurrencyRatesDataWrapper(
    val dataObject: CurrencyRatesDataObject?,
    val isSuccess: Boolean = false,
    val errorMessage: String? = null
) {
}