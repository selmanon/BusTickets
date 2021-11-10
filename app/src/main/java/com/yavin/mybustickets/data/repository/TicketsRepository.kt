package com.yavin.mybustickets.data.repository

import com.yavin.mybustickets.data.TicketDomain

interface TicketsRepository {
    suspend fun getTickets() : List<TicketDomain>
    suspend fun setPrice(type: String, price: Int)
}

