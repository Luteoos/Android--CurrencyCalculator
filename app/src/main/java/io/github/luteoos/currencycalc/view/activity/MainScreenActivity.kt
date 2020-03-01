package io.github.luteoos.currencycalc.view.activity

import android.icu.util.Currency
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import io.github.luteoos.currencycalc.R
import io.github.luteoos.currencycalc.baseAbstract.ActivityVM
import io.github.luteoos.currencycalc.data.android.CurrencyRatesViewData
import io.github.luteoos.currencycalc.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityVM<MainScreenViewModel>(){

    override val viewModel : MainScreenViewModel by viewModel()

    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showErrorSnackBar()
//        setBindings()
        coordinator.setOnClickListener {
            Currency.getInstance("EUR").currencyCode
        }
        Glide.with(this)
            .load(getString(R.string.FLAGS_URL, "eur"))
            .dontAnimate()
            .into(template_textView)
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
//        viewModel.resume()
    }

    override fun onPause() {
//        viewModel.pause()
        super.onPause()
    }

    private fun setBindings(){
        viewModel.getCurrencyLiveData().observe(this, Observer { handleCurrencyData(it) })
    }

    private fun handleCurrencyData(data: CurrencyRatesViewData){
        when(data.isSuccess){
            true -> Toast.makeText(this, " si ${data.currencies.first().currency}", Toast.LENGTH_SHORT).show()
            false -> showErrorSnackBar()
        }
    }

    private fun showErrorSnackBar(){
        Snackbar.make(coordinator, R.string.connection_error_message, Snackbar.LENGTH_INDEFINITE)
            .setAction(R.string.retry){
                viewModel.resume()
            }
            .show()
    }
}