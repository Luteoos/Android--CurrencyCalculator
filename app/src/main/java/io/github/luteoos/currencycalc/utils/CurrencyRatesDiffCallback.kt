package io.github.luteoos.currencycalc.utils

import androidx.recyclerview.widget.DiffUtil
import io.github.luteoos.currencycalc.data.android.CurrencyRateViewDataItem

class CurrencyRatesDiffCallback(private val oldList: MutableList<CurrencyRateViewDataItem>,
                                private val newList: MutableList<CurrencyRateViewDataItem>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].currency == newList[newItemPosition].currency
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].value == newList[newItemPosition].value
    }
}