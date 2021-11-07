package com.yavin.mybustickets

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TicketsSoldeAdapter : RecyclerView.Adapter<TicketsSoldeAdapter.TicketViewHolder>() {

    var tickets = mutableListOf<TicketSolde>()

    class TicketViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val ticketLibel : TextView = view.findViewById(R.id.textViewTicketLibel)
        val ticketPrice : TextView = view.findViewById(R.id.textViewTicketPrice)
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
}