package com.yavin.mybustickets.db

import android.content.Context
import androidx.room.Room

import androidx.room.RoomDatabase

import androidx.room.Database
import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.db.entities.TicketEntity


@Database(entities = [TicketEntity::class], version = 1, exportSchema = true)
abstract class BusTicketDatabase : RoomDatabase() {
    abstract fun ticketSoldDao(): TicketDao

    companion object {
        private const val DATABASE = "bustickets.db"
        private var INSTANCE: BusTicketDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): BusTicketDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            BusTicketDatabase::class.java, DATABASE
                        ).createFromAsset("database/bustickets.db").build()
                    }
                    return INSTANCE as BusTicketDatabase
                }
            }
            return INSTANCE as BusTicketDatabase
        }
    }

}
