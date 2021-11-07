package com.yavin.mybustickets.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.yavin.mybustickets.db.entities.TicketEntity

@Dao
interface TicketDao {
    @Query("SELECT * FROM TicketEntity")
    fun loadTickets(): LiveData<List<TicketEntity>>
}