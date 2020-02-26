package io.github.luteoos.currencycalc.network

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.github.luteoos.currencycalc.BuildConfig
import io.github.luteoos.currencycalc.network.`interface`.CurrencyRESTInterface
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestService(private val gson: GsonProvider, private val httpClient: OkHttpClientProvider) {

    fun getCurrencyRatesService(): CurrencyRESTInterface =
        httpClient.getDefaultOkHttpClient().let { http ->
            getRetrofitBuilder()
                .client(http)
                .build()
                .create(CurrencyRESTInterface::class.java)
        }

    fun <T> getService(serviceInterface: Class<T>) : T{
        httpClient.getDefaultOkHttpClient().let { http ->
            getRetrofitBuilder().client(http).build().let {retrofit ->
                return retrofit.create(serviceInterface)
            }
        }
    }

    private fun getRetrofitBuilder() =
        Retrofit.Builder()
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson.getDefaultGson()))
            .baseUrl(BuildConfig.BASE_URL)
}