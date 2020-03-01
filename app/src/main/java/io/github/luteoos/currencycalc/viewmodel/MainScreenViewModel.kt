package io.github.luteoos.currencycalc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.luteoos.currencycalc.`interface`.CurrencyRatesRepositoryInterface
import io.github.luteoos.currencycalc.baseAbstract.BaseViewModel
import io.github.luteoos.currencycalc.data.android.CurrencyRatesViewData
import io.github.luteoos.currencycalc.utils.Parameters
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class MainScreenViewModel(private val currencyRepository: CurrencyRatesRepositoryInterface) : BaseViewModel() {

    private val currencyList = MutableLiveData<CurrencyRatesViewData>()
    private var currentCurrency = Parameters.defaultCurrency
    private var currentCurrencyAmount : Double = 0.0
    private var disposable : Disposable? = null

    fun getCurrencyLiveData() : LiveData<CurrencyRatesViewData> = currencyList

    private fun getData(){
        disposable = currencyRepository.getCurrencyRates(currentCurrency)
            .map {
                CurrencyRatesViewData.getCalculatedRates(it, currentCurrencyAmount)
            }
            .subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currencyList.value = it
                if(!it.isSuccess)
                    pause()
            },{
                currencyList.value = CurrencyRatesViewData()
                pause()
            })
    }

    fun updateCurrencyAmount(amount: Double){
        currentCurrencyAmount = amount
    }

    fun updateMainCurrency(currency: String){
        currentCurrency = currency
        updateRepositoryFlow()
    }

    fun resume(){
        getData()
    }

    fun pause(){
        disposable?.dispose()
    }

    private fun updateRepositoryFlow(){
        pause()
        getData()
    }
}