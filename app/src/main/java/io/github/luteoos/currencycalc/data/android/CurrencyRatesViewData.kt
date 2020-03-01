package io.github.luteoos.currencycalc.data.android

data class CurrencyRatesViewData(var isSuccess: Boolean = false,
                                 var currencies: List<CurrencyRateViewDataItem> = listOf() ) {

    companion object{
        fun getCalculatedRates(wrapper: CurrencyRatesDataWrapper, baseCurrencyValue: Double) : CurrencyRatesViewData{
            if(!wrapper.isSuccess || wrapper.dataObject == null)
                return CurrencyRatesViewData()
            wrapper.dataObject.let { rates ->
                val list = mutableListOf<CurrencyRateViewDataItem>().apply {
                    add(0, CurrencyRateViewDataItem(rates.currency, baseCurrencyValue, true))
                    rates.rates.forEach {
                        add(CurrencyRateViewDataItem(it.key, it.value * baseCurrencyValue))
                    }
                }
                return CurrencyRatesViewData(wrapper.isSuccess, list)
            }
        }
    }
}