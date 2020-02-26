package io.github.luteoos.currencycalc.network

import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class OkHttpClientProvider {
    fun getDefaultOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .dispatcher(Dispatcher().apply {
                maxRequests = 1
            })
            .build()
}