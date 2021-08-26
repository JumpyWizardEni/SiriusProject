package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal
import java.time.LocalDateTime

interface TransactionsRepository {

    suspend fun getTransactions(
        id: Int,
        numberOfItems: Int,
        pageNumber: Int
    ): Flow<LoadResult<Pair<List<TransactionUiModel>, Long>>>

    suspend fun createTransaction(
        walletId: Int,
        amount: BigDecimal,
        type: TransactionType,
        category: Long,
        currency: String,
        date: LocalDateTime
    )

    suspend fun editingTransaction(
        id: Int,
        amount: BigDecimal?,
        type: TransactionType?,
        category: Long?,
        currency: String?,
        date: LocalDateTime?
    )

    suspend fun deleteTransaction(id: Int)

}