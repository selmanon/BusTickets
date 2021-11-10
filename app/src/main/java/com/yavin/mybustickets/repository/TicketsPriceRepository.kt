package com.yavin.mybustickets.repository

import com.yavin.mybustickets.data.TicketDomain

interface TicketsPriceRepository {
    suspend fun getTickets() : List<TicketDomain>
    suspend fun setPrice(type: String, price: Int)
}

