package com.yavin.mybustickets.di

import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.repository.DefaultTicketsPriceRepository
import com.yavin.mybustickets.repository.TicketsPriceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideTicketsRepository(
        dispatcher : CoroutineDispatcher,
        ticketDao: TicketDao
    ) = DefaultTicketsPriceRepository(
        dispatcher, ticketDao) as TicketsPriceRepository

}