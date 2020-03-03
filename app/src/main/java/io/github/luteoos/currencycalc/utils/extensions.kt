package io.github.luteoos.currencycalc.utils

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import io.github.luteoos.currencycalc.R
import java.util.*

fun Double.round(decimals: Int = 2): Double = "%.${decimals}f".format(this).toDouble()

fun ImageView.loadFlag(currencyName: String){
        Glide.with(context)
            .load(context.getString(R.string.FLAGS_URL, currencyName.toLowerCase(Locale.getDefault())))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .into(this)
}