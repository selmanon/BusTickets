package com.yavin.mybustickets.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.yavin.mybustickets.TicketSolde
import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.di.DefaultDispatcher
import com.yavin.mybustickets.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultTicketsPriceRepository @Inject constructor(
    @IoDispatcher val defaultDispatcher: CoroutineDispatcher,
    private val ticketDao: TicketDao
) : TicketsPriceRepository {


    override suspend fun getTickets(): List<TicketSolde> {
        val tickets = arrayListOf<TicketSolde>()
        withContext(defaultDispatcher) {
            ticketDao.loadTickets().forEach {
                tickets.add(TicketSolde(it.ticketLabel.toString(), it.ticketPrice!!.toInt()))
            }
        }

        return tickets
    }
}
