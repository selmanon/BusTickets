package com.yavin.mybustickets.domaine.repository

import com.yavin.mybustickets.domaine.TransactionDomain
import com.yavin.mybustickets.domaine.TransactionStatus
import com.yavin.mybustickets.db.dao.TransactionDao
import com.yavin.mybustickets.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DefaultTransactionRepository @Inject constructor(
    @IoDispatcher val defaultDispatcher: CoroutineDispatcher,
    private val transactionDao: TransactionDao
) : TransactionRepository {
    override suspend fun getTransaction(): List<TransactionDomain> {
        val transactions = arrayListOf<TransactionDomain>()
        withContext(defaultDispatcher) {
            transactionDao.getTransactions().forEach {
                transactions.add(TransactionDomain(it.date, it.price, TransactionStatus.valueOf(it.status)))
            }
        }

        return transactions
    }
}