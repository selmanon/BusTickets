package com.yavin.mybustickets.repository

import com.yavin.mybustickets.data.TicketSolde
import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
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

    override suspend fun setPrice(type: String, price: Int)  {
        withContext(defaultDispatcher) {
            ticketDao.setTicketPrice(type, price)
        }
    }
}
