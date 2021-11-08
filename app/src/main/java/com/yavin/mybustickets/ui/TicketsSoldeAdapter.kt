package com.yavin.mybustickets.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.yavin.mybustickets.R
import com.yavin.mybustickets.TicketSolde

class TicketsSoldeAdapter : RecyclerView.Adapter<TicketsSoldeAdapter.TicketViewHolder>() {
    var onItemsCountChanged: ((TicketPriceAndItems) -> Unit)? = null
    var tickets = mutableListOf<TicketSolde>()

    inner class TicketViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ticketLibel : TextView = view.findViewById(R.id.textViewTicketLibel)
        val ticketPrice : TextView = view.findViewById(R.id.textViewTicketPrice)
        val items : EditText = view.findViewById(R.id.editTextNumberOfItems)

        init {
            items.doOnTextChanged { text, start, before, count ->
                onItemsCountChanged?.invoke(TicketPriceAndItems(tickets[adapterPosition], text.toString().toInt()))
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TicketViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.ticket_item, viewGroup, false)

        return TicketViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: TicketViewHolder, position: Int) {
        viewHolder.ticketLibel.text = tickets[position].ticketLabel
        viewHolder.ticketPrice.text = tickets[position].ticketPrice.toString()
    }

    override fun getItemCount(): Int = tickets.size

    fun updateData(newData: List<TicketSolde>) {
        tickets.clear()
        tickets.addAll(newData)
        notifyDataSetChanged()
    }

    data class TicketPriceAndItems(val ticketSolde: TicketSolde, val items : Int)
}