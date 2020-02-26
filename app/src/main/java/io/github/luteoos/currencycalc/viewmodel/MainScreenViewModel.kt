package io.github.luteoos.currencycalc.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.github.luteoos.currencycalc.baseAbstract.BaseViewModel
import io.github.luteoos.currencycalc.repository.CurrencyRatesRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable

class MainScreenViewModel(private val currencyRepository: CurrencyRatesRepository) : BaseViewModel() {

    private val currency = MutableLiveData<String>()
    private lateinit var disposable : Disposable
    fun getLiveData() : LiveData<String> = currency

    fun getData(){
        currencyRepository.getCurrencyRates()//todo multiply currency and rate, to new object that is fitting view needs, containing isSuccess
                //if not success, view show snackbar and detach disposable -> Retry reaatahes flow
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                currency.value = it.dataObject?.currency ?: "pusto :("
            },{
                it.message
            })
    }

    fun pause(){
        disposable.dispose()
    }
}