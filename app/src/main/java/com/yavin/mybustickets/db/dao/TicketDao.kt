package com.yavin.mybustickets.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.yavin.mybustickets.db.entities.TicketEntity

@Dao
interface TicketDao {
    @Query("SELECT * FROM TicketEntity")
    suspend fun loadTickets(): List<TicketEntity>

    @Query("UPDATE ticketentity SET ticketPrice=:price WHERE ticketLabel=:type")
    suspend fun setTicketPrice(type: String, price: Int)
}