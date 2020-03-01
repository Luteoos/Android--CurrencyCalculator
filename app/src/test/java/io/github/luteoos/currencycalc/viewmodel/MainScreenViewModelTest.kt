package io.github.luteoos.currencycalc.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.TestObserver
import io.github.luteoos.currencycalc.`interface`.CurrencyRatesRepositoryInterface
import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataObject
import io.github.luteoos.currencycalc.data.android.CurrencyRatesDataWrapper
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainScreenViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    lateinit var repo : CurrencyRatesRepositoryInterface

    @Before
    fun setUp(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline()}
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
        RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @Test
    fun getData_Success_LiveDataSuccessTrue(){
        Mockito.`when`(repo.getCurrencyRates("EUR")).thenReturn(Flowable.just(
            CurrencyRatesDataWrapper(CurrencyRatesDataObject("EUR", mapOf()),true)
        ))
        val vm = MainScreenViewModel(repo)
        vm.resume()
        TestObserver.test(vm.getCurrencyLiveData())
            .assertHasValue()
            .assertValue { it.isSuccess }
    }

    @Test
    fun getData_Fail_LiveDataSuccessFalse(){
        Mockito.`when`(repo.getCurrencyRates("EUR")).thenReturn(Flowable.just(
            CurrencyRatesDataWrapper(null, errorMessage = "test error")
        ))
        val vm = MainScreenViewModel(repo)
        vm.resume()
        TestObserver.test(vm.getCurrencyLiveData())
            .assertHasValue()
            .assertValue { !it.isSuccess }
    }

    @Test
    fun getData_Exception_LiveDataSuccessFalse(){
        Mockito.`when`(repo.getCurrencyRates("EUR"))
            .thenReturn(Flowable.error(Throwable("test throw")))
        val vm = MainScreenViewModel(repo)
        vm.resume()
        TestObserver.test(vm.getCurrencyLiveData())
            .assertHasValue()
            .assertValue { !it.isSuccess }
    }

    @Test
    fun getData_ValueCalculated_LiveDataSuccessTrue(){
        Mockito.`when`(repo.getCurrencyRates("EUR")).thenReturn(Flowable.just(
            CurrencyRatesDataWrapper(CurrencyRatesDataObject("EUR", mapOf(Pair("USD", 2.0))),true)
        ))
        val vm = MainScreenViewModel(repo)
        vm.updateCurrencyAmount(30.0)
        vm.resume()
        TestObserver.test(vm.getCurrencyLiveData())
            .assertHasValue()
            .assertValue { it.isSuccess }
            .assertValue { it.currencies[1].value == 60.0 }
            .assertValue { it.currencies[0].isBase }
    }

}