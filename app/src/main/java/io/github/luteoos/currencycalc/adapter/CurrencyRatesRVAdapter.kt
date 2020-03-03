package io.github.luteoos.currencycalc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import io.github.luteoos.currencycalc.R
import io.github.luteoos.currencycalc.data.android.CurrencyRateViewDataItem
import io.github.luteoos.currencycalc.utils.Parameters
import io.github.luteoos.currencycalc.utils.loadFlag
import io.reactivex.rxjava3.subjects.PublishSubject

/**
 * Passiflora on fire
 */
class CurrencyRatesRVAdapter : RecyclerView.Adapter<CurrencyRatesRVAdapter.CurrencyRatesVH>() {

    private val data = mutableListOf<CurrencyRateViewDataItem>()
    val onClickItem = PublishSubject.create<CurrencyRateViewDataItem>()!!
    val onEditTextModified = PublishSubject.create<Double>()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRatesVH {
        return CurrencyRatesVH(LayoutInflater.from(parent.context).inflate(R.layout.rv_currency, parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CurrencyRatesVH, position: Int) {
        holder.setIsRecyclable(false)
        holder.bindTo(getItem(position))
        holder.itemView.setOnClickListener {
            notifyItemMoved(position, 0)
            onClickItem.onNext(getItem(position))
        }
    }

    override fun onBindViewHolder(holder: CurrencyRatesVH, position: Int, payloads: MutableList<Any>) {
        if(payloads.isEmpty())
            onBindViewHolder(holder,position)
        else{
            payloads.first().let {payload ->
                if(payload is Int)
                    when(payload){
                        Parameters.payloadCurrencyValueUpdate -> holder.updateHolderCurrencyValue(getItem(position))
                        else -> super.onBindViewHolder(holder, position, payloads)
                    }
            }
        }
    }

    fun updateDataList(list: MutableList<CurrencyRateViewDataItem>){
        if(data.firstOrNull()?.currency != list.firstOrNull()?.currency) {
            data.clear()
            data.addAll(list)
            notifyDataSetChanged()
        } else {
            data.clear()
            data.addAll(list)
            notifyItemRangeChanged(0, itemCount, Parameters.payloadCurrencyValueUpdate)
        }
    }

    private fun getItem(position: Int) = data[position]

    inner class CurrencyRatesVH(view: View) : RecyclerView.ViewHolder(view){
        private val currencyName = view.findViewById<TextView>(R.id.tvCurrencyName)
        private val currencyValue = view.findViewById<EditText>(R.id.etCurrencyValue)
        private val flagIcon = view.findViewById<ImageView>(R.id.ivFlagIcon)
        private val currencyFullName = view.findViewById<TextView>(R.id.tvCurrencyFullName)

        fun updateHolderCurrencyValue(item: CurrencyRateViewDataItem){
            if(!item.isBase){
                currencyValue.setText(item.value.toString())
            }
        }

        fun bindTo(item: CurrencyRateViewDataItem){
            flagIcon.loadFlag(item.currency.currencyCode)
            currencyName.text = item.currency.currencyCode
            currencyFullName.text = item.currency.displayName
            currencyValue.apply {
                setText(item.value.toString(), TextView.BufferType.NORMAL)
                if(item.isBase){
                    isEnabled = true
                    requestFocus()
                    setSelection(currencyValue.text.count())
                    textChanges().skipInitialValue().doOnNext {
                        onEditTextModified.onNext(it.toString().toDoubleOrNull() ?: 0.0)
                    }.subscribe()
                }else{
                    currencyValue.isEnabled = false
                }
            }
        }
    }
}