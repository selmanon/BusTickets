package com.yavin.mybustickets.domaine.repository

import com.yavin.mybustickets.domaine.TicketDomain
import com.yavin.mybustickets.domaine.TicketType
import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject


class DefaultTicketsRepository @Inject constructor(
    @IoDispatcher val defaultDispatcher: CoroutineDispatcher,
    private val ticketDao: TicketDao
) : TicketsRepository {


    override suspend fun getTickets(): List<TicketDomain> {
        val tickets = arrayListOf<TicketDomain>()
        withContext(defaultDispatcher) {
            ticketDao.loadTickets().forEach {
                tickets.add(TicketDomain(TicketType.valueOf(it.ticketLabel), it.ticketPrice!!.toInt()))
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
