package com.yavin.mybustickets.repository

import com.yavin.mybustickets.data.TicketSolde

interface TicketsPriceRepository {
    suspend fun getTickets() : List<TicketSolde>
    suspend fun setPrice(type: String, price: Int)
}

