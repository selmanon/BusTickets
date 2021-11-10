package com.yavin.mybustickets.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.yavin.mybustickets.db.entities.TransactionEntity


@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    suspend fun getTransactions() : List<TransactionEntity>
}
