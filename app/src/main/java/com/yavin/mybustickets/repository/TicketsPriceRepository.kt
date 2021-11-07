package com.yavin.mybustickets.repository

import androidx.lifecycle.LiveData
import com.yavin.mybustickets.TicketSolde

interface TicketsPriceRepository {
    suspend fun getTickets() : List<TicketSolde>
}

