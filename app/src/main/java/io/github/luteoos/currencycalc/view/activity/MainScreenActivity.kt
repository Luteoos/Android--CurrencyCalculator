package io.github.luteoos.currencycalc.view.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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
        viewModel.updateCurrencyAmount(0.0)
        currencyRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainScreenActivity)
            adapter = this@MainScreenActivity.adapter
            this@MainScreenActivity.adapter.onClickItem.subscribe {
                viewModel.updateMainCurrency(it)
                scrollToPosition(0)
            }
            this@MainScreenActivity.adapter.onEditTextModified.subscribe {
                viewModel.updateCurrencyAmount(it)
            }
        }
//        constraintLayout.setOnClickListener {
//            Currency.getInstance("EUR").currencyCode
//        }
//        Glide.with(this)
//            .load(getString(R.string.FLAGS_URL, "eur"))
//            .dontAnimate()
//            .into(template_textView)
//        template_textView.setOnClickListener {
//            viewModel.getData()
//        }
//        viewModel.getLiveData().observe(this, Observer {
//            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
//        })
//        tv.setOnClickListener {
//            viewModel.pause()
//        }
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
    }

    private fun handleCurrencyData(data: CurrencyRatesViewData){
        when(data.isSuccess){
            true -> adapter.updateDataList(data.currencies)
            false -> showErrorSnackBar()
        }
    }

    private fun showErrorSnackBar(){
        Snackbar.make(layout, R.string.connection_error_message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry){
                viewModel.resume()
            }
            .show()
    }
}