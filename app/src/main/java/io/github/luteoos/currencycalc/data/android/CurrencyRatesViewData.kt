package io.github.luteoos.currencycalc.data.android

import android.icu.util.Currency
import io.github.luteoos.currencycalc.utils.round

data class CurrencyRatesViewData(var isSuccess: Boolean = false,
                                 var currencies: MutableList<CurrencyRateViewDataItem> = mutableListOf() ) {

    companion object{
        fun getCalculatedRates(wrapper: CurrencyRatesDataWrapper, baseCurrencyValue: Double) : CurrencyRatesViewData{
            if(!wrapper.isSuccess || wrapper.dataObject == null)
                return CurrencyRatesViewData()
            wrapper.dataObject.let { rates ->
                val list = mutableListOf<CurrencyRateViewDataItem>().apply {
                    add(0, CurrencyRateViewDataItem(getCurrency(rates.currency), baseCurrencyValue, true))
                    rates.rates.forEach {
                        add(CurrencyRateViewDataItem(getCurrency(it.key), (it.value * baseCurrencyValue).round()))
                    }
                }
                return CurrencyRatesViewData(wrapper.isSuccess, list)
            }
        }

        private fun getCurrency(code: String) = Currency.getInstance(code)
    }
}