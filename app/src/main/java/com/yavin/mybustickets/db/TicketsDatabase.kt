package com.yavin.mybustickets

import android.content.ContentValues
import android.content.Context
import androidx.room.Room

import androidx.room.RoomDatabase


import androidx.room.Database
import androidx.room.OnConflictStrategy
import androidx.sqlite.db.SupportSQLiteDatabase
import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.db.entities.TicketEntity


@Database(entities = [TicketEntity::class], version = 1)
abstract class TicketSoldRoomDatabase : RoomDatabase() {
    abstract fun ticketSoldDao(): TicketDao

    companion object {
        private lateinit var INSTANCE: TicketSoldRoomDatabase
        fun getDatabase(context: Context): TicketSoldRoomDatabase {
            if (INSTANCE == null) {
                synchronized(TicketSoldRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            TicketSoldRoomDatabase::class.java, "TicketSold_database"
                        )
                            .fallbackToDestructiveMigration()
                            .addCallback(object : RoomDatabase.Callback() {
                                override fun onCreate(db: SupportSQLiteDatabase) {
                                    super.onCreate(db)
                                    tickets.forEach {
                                        val ticket = ContentValues().apply {
                                            put(it.uid.toString(), it.uid)
                                            put(it.ticketLabel.toString(), it.ticketLabel)
                                            put(it.ticketPrice.toString(), it.ticketPrice)
                                        }

                                        db.insert(
                                            TicketEntity::class.simpleName,
                                            OnConflictStrategy.REPLACE,
                                            ticket
                                        )
                                    }
                                }
                            })
                            .build()
                    }
                }
            }
            return INSTANCE
        }

        val tickets: List<TicketEntity>
            get() =
                listOf(
                    TicketEntity(
                        uid = 1,
                        ticketLabel = "Single Journey",
                        ticketPrice = 110
                    ),
                    TicketEntity(
                        uid = 2,
                        ticketLabel = "Day",
                        ticketPrice = 250
                    ),
                    TicketEntity(
                        uid = 3,
                        ticketLabel = "Week",
                        ticketPrice = 1200
                    )
                )
    }
}





