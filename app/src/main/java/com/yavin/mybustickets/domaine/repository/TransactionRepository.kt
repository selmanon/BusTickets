package com.yavin.mybustickets.domaine.repository

import com.yavin.mybustickets.domaine.TransactionDomain

interface TransactionRepository {
    suspend fun getTransaction() : List<TransactionDomain>
}

