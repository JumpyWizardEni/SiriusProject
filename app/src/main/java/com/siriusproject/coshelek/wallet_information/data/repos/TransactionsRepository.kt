package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal

interface TransactionsRepository {

    suspend fun getTransactionsList(): Flow<>

    suspend fun createTransaction(
        amount: BigDecimal,
        type: TransactionType,
        category: String,
        currency: String
    )

    suspend fun editingTransaction(
        id: Int,
        amount: BigDecimal,
        type: TransactionType,
        category: String,
        currency: String
    )

    suspend fun deleteTransaction(id: Int)

}