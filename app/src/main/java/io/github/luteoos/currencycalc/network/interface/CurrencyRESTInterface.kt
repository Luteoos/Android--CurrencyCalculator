package io.github.luteoos.currencycalc.network.`interface`

import io.github.luteoos.currencycalc.data.rest.CurrencyRatesDataModelRest
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRESTInterface {
    @GET("latest")
    fun getLatestCurrencyRates(@Query("base")base: String) : Single<Response<CurrencyRatesDataModelRest>>
}