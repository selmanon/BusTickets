package com.yavin.mybustickets.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val uid: Int = 0,
    @ColumnInfo(name = "ticketLabel")
    val ticketLabel: String,
    @ColumnInfo(name = "ticketPrice")
    val ticketPrice: Int
)