package com.yavin.mybustickets.domaine


data class TransactionDomain(
    val date: String,
    val price: Int,
    val status: TransactionStatus
)
