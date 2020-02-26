package io.github.luteoos.currencycalc.data.rest

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RatesDataModelRest(
    @Expose
    @SerializedName("baseCurrency")
    val baseCurrency: String,
    @Expose
    @SerializedName("rates")
    val rates: Map<String, Double>
)