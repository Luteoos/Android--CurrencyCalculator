package io.github.luteoos.currencycalc.repository

import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataObject
import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataWrapper
import io.github.luteoos.currencycalc.network.RestService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class CurrencyRatesRepository(private val currencyService: RestService) {


    fun getCurrencyRates() : Flowable<CurrencyRatesDataWrapper> {
       return currencyService.getCurrencyRatesService().getLatestCurrencyRates("EUR")
           .delay(1, TimeUnit.SECONDS)
           .repeat()
           .map {response ->
               when(response.code()){
                   200 -> CurrencyRatesDataWrapper(CurrencyRatesDataObject(response.body()!!), true)
                   else -> CurrencyRatesDataWrapper(null, errorMessage = response.message())
               }
           }
           .onErrorReturn {throwable ->
               CurrencyRatesDataWrapper(null, errorMessage = throwable.message)
           }
            .subscribeOn(Schedulers.io())
    }
}