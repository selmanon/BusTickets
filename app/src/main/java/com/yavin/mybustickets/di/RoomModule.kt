package com.yavin.mybustickets.di

import android.content.Context
import androidx.room.Room
import com.yavin.mybustickets.db.BusTicketDatabase
import com.yavin.mybustickets.db.dao.TicketDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RoomModule {
    @Singleton
    @Provides
    fun provideTicketDatabase(@ApplicationContext context: Context): BusTicketDatabase {
        return BusTicketDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideTicketDao(ticketsDatabase: BusTicketDatabase): TicketDao {
        return ticketsDatabase.ticketSoldDao()
    }
}