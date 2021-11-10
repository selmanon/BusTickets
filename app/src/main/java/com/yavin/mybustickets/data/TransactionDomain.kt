package com.yavin.mybustickets.data


data class TransactionDomain(
    val date: String,
    val price: Int,
    val status: TransactionStatus
)
