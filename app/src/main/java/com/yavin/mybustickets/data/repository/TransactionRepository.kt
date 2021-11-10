package com.yavin.mybustickets.data.repository

import com.yavin.mybustickets.data.TransactionDomain

interface TransactionRepository {
    suspend fun getTransaction() : List<TransactionDomain>
}

