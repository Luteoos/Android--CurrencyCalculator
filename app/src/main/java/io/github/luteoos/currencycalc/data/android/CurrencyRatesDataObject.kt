package io.github.luteoos.currencycalc.data.android

import io.github.luteoos.currencycalc.data.rest.CurrencyRatesDataModelRest

data class CurrencyRatesDataObject(
    val currency: String,
    val rates : Map<String, Double>,
    val size : Int = rates.size
) {
   constructor(restDataObject: CurrencyRatesDataModelRest) :
           this(restDataObject.baseCurrency,
               restDataObject.rates)
}