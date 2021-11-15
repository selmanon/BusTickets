package com.yavin.mybustickets.di

import com.yavin.mybustickets.db.dao.TicketDao
import com.yavin.mybustickets.domaine.repository.DefaultTicketsRepository
import com.yavin.mybustickets.domaine.repository.DefaultTransactionRepository
import com.yavin.mybustickets.domaine.repository.TicketsRepository
import com.yavin.mybustickets.domaine.repository.TransactionRepository
import com.yavin.mybustickets.db.dao.TransactionDao
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
        @IoDispatcher dispatcher : CoroutineDispatcher,
        ticketDao: TicketDao
    ) = DefaultTicketsRepository(
        dispatcher, ticketDao) as TicketsRepository

    @Singleton
    @Provides
    fun provideTransactionsRepository(
        @IoDispatcher dispatcher : CoroutineDispatcher,
        transactionDao: TransactionDao
    ) = DefaultTransactionRepository(
        dispatcher, transactionDao) as TransactionRepository
}