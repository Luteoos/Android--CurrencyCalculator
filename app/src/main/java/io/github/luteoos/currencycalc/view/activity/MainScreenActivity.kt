package io.github.luteoos.currencycalc.view.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import io.github.luteoos.currencycalc.R
import io.github.luteoos.currencycalc.adapter.CurrencyRatesRVAdapter
import io.github.luteoos.currencycalc.baseAbstract.ActivityVM
import io.github.luteoos.currencycalc.data.android.CurrencyRatesViewData
import io.github.luteoos.currencycalc.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityVM<MainScreenViewModel>(){

    override val viewModel : MainScreenViewModel by viewModel()
    private val adapter : CurrencyRatesRVAdapter by inject()

    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBindings()
        setRVAdapter()
     }

    override fun onResume() {
        super.onResume()
        viewModel.resume()
    }

    override fun onPause() {
        viewModel.pause()
        super.onPause()
    }

    private fun setBindings(){
        viewModel.getCurrencyLiveData().observe(this, Observer { handleCurrencyData(it) })
        currencyRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                hideKeyboard()
            }
        })
    }

    private fun setRVAdapter(){
        currencyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainScreenActivity)
            adapter = this@MainScreenActivity.adapter
        }
            adapter.onClickItem.subscribe {
                viewModel.updateMainCurrency(it.getCurrency(), it.value)
                currencyRecyclerView.scrollToPosition(0)
            }
            adapter.onEditTextModified.subscribe {
                viewModel.updateCurrencyAmount(it)
            }
    }

    private fun handleCurrencyData(data: CurrencyRatesViewData){
        when(data.isSuccess){
            true -> adapter.updateDataList(data.currencies)
            false -> showErrorSnackBar()
        }
    }

    private fun showErrorSnackBar(){
        Snackbar.make(layout, R.string.connection_error_message, Snackbar.LENGTH_INDEFINITE)
            .setActionTextColor(getColor(R.color.red))
            .setAction(R.string.retry){
                viewModel.resume()
            }
            .show()
    }
}