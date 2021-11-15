package com.yavin.mybustickets.ui.transaction

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yavin.mybustickets.R
import com.yavin.mybustickets.domaine.TransactionDomain
import java.text.NumberFormat
import java.util.*

class TransactionAdapter : RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {
    var transactions = mutableListOf<TransactionDomain>()

    inner class TransactionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var transactionDate: TextView = view.findViewById(R.id.date)
        var transactionStatus: TextView = view.findViewById(R.id.status)
        var transactionPrice: TextView = view.findViewById(R.id.price)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val rootView =
            LayoutInflater.from(parent.context).inflate(R.layout.transaction_item, parent, false)
        return TransactionViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        transactions[position].run {
            holder.transactionDate.text = this.date
            holder.transactionStatus.text = this.status.status
            holder.transactionPrice.text = formatPrice(this.price)
        }
    }
    override fun getItemCount(): Int = transactions.size

    fun updateData(newData: List<TransactionDomain>) {
        transactions.clear()
        transactions.addAll(newData)
        notifyDataSetChanged()
    }

    private fun formatPrice(price: Int): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance("EUR")
        formatter.maximumFractionDigits = 2
        return formatter.format(price / 100.00)
    }

}