package com.yavin.mybustickets.ui.payment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.yavin.mybustickets.R
import com.yavin.mybustickets.data.TicketDomain
import com.yavin.mybustickets.data.TicketType
import java.text.NumberFormat
import java.util.*

class TicketsSoldeAdapter : RecyclerView.Adapter<TicketsSoldeAdapter.TicketViewHolder>() {
    var onDayItemsCountChanged: ((TicketPriceAndItems) -> Unit)? = null
    var onSingleItemsCountChanged: ((TicketPriceAndItems) -> Unit)? = null
    var onWeekItemsCountChanged: ((TicketPriceAndItems) -> Unit)? = null

    var tickets = mutableListOf<TicketDomain>()

    inner class TicketViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ticketLibel: TextView = view.findViewById(R.id.textViewTicketLibel)
        val ticketPrice: TextView = view.findViewById(R.id.textViewTicketPrice)
        val items: EditText = view.findViewById(R.id.editTextNumberOfItems)

        init {
            items.doOnTextChanged { text, start, before, count ->
                if (tickets[adapterPosition].ticketLabel == TicketType.DAY) {
                    onDayItemsCountChanged?.invoke(
                        TicketPriceAndItems(
                            tickets[adapterPosition],
                            text.toString().toInt()
                        )
                    )
            }
                if (tickets[adapterPosition].ticketLabel == TicketType.SINGLE) {
                    onSingleItemsCountChanged?.invoke(
                        TicketPriceAndItems(
                            tickets[adapterPosition],
                            text.toString().toInt()
                        )
                    )
                }
                if (tickets[adapterPosition].ticketLabel == TicketType.WEEK) {
                    onWeekItemsCountChanged?.invoke(
                        TicketPriceAndItems(
                            tickets[adapterPosition],
                            text.toString().toInt()
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.ticket_item, viewGroup, false)

        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TicketViewHolder, position: Int) {
        viewHolder.ticketLibel.text = tickets[position].ticketLabel.type
        viewHolder.ticketPrice.text = formatPrice(tickets[position].ticketPrice)
    }

    override fun getItemCount(): Int = tickets.size

    fun updateData(newData: List<TicketDomain>) {
        tickets.clear()
        tickets.addAll(newData)
        notifyDataSetChanged()
    }

    private fun formatPrice(price: Int): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.currency = Currency.getInstance("EUR")
        formatter.maximumFractionDigits = 2
        return formatter.format(price / 100.00)
    }

    data class TicketPriceAndItems(val ticketDomain: TicketDomain, val items: Int)
}