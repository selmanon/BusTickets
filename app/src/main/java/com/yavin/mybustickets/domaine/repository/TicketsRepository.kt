package com.yavin.mybustickets.domaine.repository

import com.yavin.mybustickets.domaine.TicketDomain

interface TicketsRepository {
    suspend fun getTickets() : List<TicketDomain>
    suspend fun setPrice(type: String, price: Int)
}

