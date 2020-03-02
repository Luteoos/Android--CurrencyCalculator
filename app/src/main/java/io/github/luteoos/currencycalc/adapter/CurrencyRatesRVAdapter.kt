package io.github.luteoos.currencycalc.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jakewharton.rxbinding3.widget.textChanges
import io.github.luteoos.currencycalc.R
import io.github.luteoos.currencycalc.data.android.CurrencyRateViewDataItem
import io.reactivex.Observable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.android.synthetic.main.rv_currency.view.*

class CurrencyRatesRVAdapter(private val context: Context) : RecyclerView.Adapter<CurrencyRatesRVAdapter.CurrencyRatesVH>() {
    private val data = mutableListOf<CurrencyRateViewDataItem>()
    val onClickItem = PublishSubject.create<String>()!!
    var onEditTextModifiedd : Observable<CharSequence> = Observable.empty()
    var onEditTextModified = PublishSubject.create<Double>()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyRatesVH {
        return CurrencyRatesVH(LayoutInflater.from(context).inflate(R.layout.rv_currency, parent, false))
    }

    fun updateDataList(list: MutableList<CurrencyRateViewDataItem>){
//        DiffUtil.calculateDiff(CurrencyRatesDiffCallback(data, list)).let {
        if(data.firstOrNull()?.currency != list.firstOrNull()?.currency){
            data.clear()
            data.addAll(list)
            notifyDataSetChanged()
        }else
        {
            data.clear()
            data.addAll(list)
            notifyItemRangeChanged(1, data.size )
        }
//            it.dispatchUpdatesTo(this)
//        }
//        data.re
//        data.addAll(list)
//        notifyItemRangeChanged(1, itemCount)
    }

    override fun getItemCount() = data.size

//    override fun onBindViewHolder(
//        holder: CurrencyRatesVH,
//        position: Int,
//        payloads: MutableList<Any>
//    ) {
//        if(payloads.isEmpty())
//            onBindViewHolder(holder,position)
//        else{
//            payloads.forEach { //payload[1]
//                if(it is CurrencyRateViewDataItem)
//                   holder.update(it)
//            }
//        }
//    }

    override fun onBindViewHolder(holder: CurrencyRatesVH, position: Int) {
//        holder.let {
//            it.currencyName.text = data[position].currency
//            it.currencyValue.setText(data[position].value.toString(), TextView.BufferType.EDITABLE)
//        }
//        holder.currencyValue.isFocusable = position == 0
        holder.itemView.setOnClickListener {
            onClickItem.onNext(data[position].currency)
            notifyItemMoved(position, 0)
        }
        holder.update(data[position])
//        if(position == 0){
//            onEditTextModifiedd =
        if(data[position].isBase)
                holder.currencyValue.textChanges().subscribe{
                onEditTextModified.onNext(it.toString().toDoubleOrNull() ?: 0.0)
            }
//        }
    }

    inner class CurrencyRatesVH(view: View) : RecyclerView.ViewHolder(view){
        val currencyName = view.tvCurrencyName
        val currencyValue = view.etCurrencyValue

        fun update(item: CurrencyRateViewDataItem){
            if(item.isBase){
                currencyValue.isClickable = true
                currencyValue.isFocusable = true
//                currencyValue.requestFocus()
            }
            else{
                currencyValue.isClickable = false
                currencyValue.isFocusable = false
            }
            currencyValue.setText(item.value.toString())
            if(currencyName.text != item.currency)
                currencyName.text = item.currency
        }
    }
}