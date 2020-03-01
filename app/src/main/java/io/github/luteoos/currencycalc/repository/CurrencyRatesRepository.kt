package io.github.luteoos.currencycalc.repository

import io.github.luteoos.currencycalc.`interface`.CurrencyRatesRepositoryInterface
import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataObject
import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataWrapper
import io.github.luteoos.currencycalc.network.RestService
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class CurrencyRatesRepository(private val currencyService: RestService) : CurrencyRatesRepositoryInterface{


    override fun getCurrencyRates(currency: String) : Flowable<CurrencyRatesDataWrapper> {
       return currencyService.getCurrencyRatesService().getLatestCurrencyRates(currency)
           .repeatWhen { getIntervalFlowable() }
           .doOnNext { Timber.e("TEST// REST") }
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

    private fun getIntervalFlowable() =
        Flowable.interval(1, TimeUnit.SECONDS)
            .onBackpressureDrop { Timber.e("TEST// INTERVAL DROP") }
            .doOnNext { Timber.e("TEST// INTERVAL $it") }
}