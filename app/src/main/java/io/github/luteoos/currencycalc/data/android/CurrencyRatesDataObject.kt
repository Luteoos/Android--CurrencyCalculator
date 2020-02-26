package io.github.luteoos.currencycalc.data.android

import io.github.luteoos.currencycalc.data.rest.RatesDataModelRest

data class CurrencyRatesDataObject(
    val currency: String,
    val rates : Map<String, Double>,
    val size : Int = rates.size
) {
   constructor(restDataObject: RatesDataModelRest) :
           this(restDataObject.baseCurrency,
               restDataObject.rates)
}