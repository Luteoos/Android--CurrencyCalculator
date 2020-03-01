package io.github.luteoos.currencycalc.repository

import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataWrapper
import io.github.luteoos.currencycalc.data.rest.CurrencyRatesDataModelRest
import io.github.luteoos.currencycalc.network.RestService
import io.github.luteoos.currencycalc.network.`interface`.CurrencyRESTInterface
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.subscribers.TestSubscriber
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class CurrencyRatesRepositoryTest {

    private val currency = "EUR"
    @Mock
    lateinit var restService: RestService
    @Mock
    lateinit var restInterface : CurrencyRESTInterface

    @Test
    fun getCurrencyRates_RequestFailed_ReturnWrapperSuccessFalse(){
        val repository = CurrencyRatesRepository(restService)
        Mockito.`when`(restService.getCurrencyRatesService()).thenReturn(restInterface)
        Mockito.`when`(restInterface.getLatestCurrencyRates(currency))
            .thenReturn(
                Single.just(
                    Response.error(404,
                        "{}".toResponseBody("application/json".toMediaTypeOrNull())
                    )))
        val testSub = TestSubscriber<CurrencyRatesDataWrapper>()
        repository.getCurrencyRates(currency).subscribe(testSub)
        testSub
            .awaitCount(1)
            .assertValueAt(0) {!it.isSuccess}
    }

    @Test
    fun getCurrencyRates_RequestSuccess_ReturnWrapperSuccessTrue(){
        val repository = CurrencyRatesRepository(restService)
        val testSub = TestSubscriber<CurrencyRatesDataWrapper>()
        Mockito.`when`(restService.getCurrencyRatesService()).thenReturn(restInterface)
        Mockito.`when`(restInterface.getLatestCurrencyRates(currency))
            .thenReturn(
                Single.just(
                    Response.success(CurrencyRatesDataModelRest("EUR", mapOf(Pair("PLN",1.0)))
                    )))
        repository.getCurrencyRates(currency).subscribe(testSub)
        testSub
            .awaitCount(1)
            .assertValueAt(0) {it.isSuccess}
    }

    @Test
    fun getCurrencyRates_ErrorThrow_ReturnWrapperSuccessFalse(){
        val repository = CurrencyRatesRepository(restService)
        val testSub = TestSubscriber<CurrencyRatesDataWrapper>()
        val message = "test throw"
        Mockito.`when`(restService.getCurrencyRatesService()).thenReturn(restInterface)
        Mockito.`when`(restInterface.getLatestCurrencyRates(currency))
            .thenReturn(
                Single.error(Throwable(message)))
        repository.getCurrencyRates(currency).subscribe(testSub)
        testSub
            .awaitCount(1)
            .assertValueAt(0) {!it.isSuccess && it.errorMessage == message}
    }
}