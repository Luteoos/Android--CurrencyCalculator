package io.github.luteoos.currencycalc.view.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import io.github.luteoos.currencycalc.R
import io.github.luteoos.currencycalc.baseAbstract.ActivityVM
import io.github.luteoos.currencycalc.viewmodel.MainScreenViewModel
import kotlinx.android.synthetic.main.activity_splash_screen.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenActivity : ActivityVM<MainScreenViewModel>(){

    override val viewModel : MainScreenViewModel by viewModel()

    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Glide.with(this)
            .load(getString(R.string.FLAGS_URL, "ph"))
            .dontAnimate()
            .into(template_textView)
        template_textView.setOnClickListener {
            viewModel.getData()
        }
        viewModel.getLiveData().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
        tv.setOnClickListener {
            viewModel.pause()
        }
     }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }
}