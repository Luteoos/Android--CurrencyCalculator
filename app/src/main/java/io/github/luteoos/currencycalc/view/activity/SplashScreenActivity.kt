package io.github.luteoos.currencycalc.view.activity

import android.os.Bundle
import io.github.luteoos.currencycalc.R
import io.github.luteoos.currencycalc.baseAbstract.ActivityNoVM

class SplashScreenActivity : ActivityNoVM() {

    override fun getLayoutID(): Int = R.layout.activity_splash_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startMainScreenActivity()
    }

    private fun startMainScreenActivity(){
        TODO("NOT IMPLEMENTED")
    }
}