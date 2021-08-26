package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_information.data.model.TransactionCreateBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionEditBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.data.network.TransactionService
import com.siriusproject.coshelek.wallet_information.domain.mapper.TransactionMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
import java.net.ConnectException
import java.time.LocalDateTime
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val transactionRemote: TransactionService,
    private val mapper: TransactionMapper
) : TransactionsRepository {

    override suspend fun getTransactions(
        id: Int,
        numberOfItems: Int,
        pageNumber: Int
    ): Flow<LoadResult<Pair<List<TransactionUiModel>, Long>>> =
        flow {
            try {
                val result = transactionRemote.getTransactions(
                    id,
                    numberOfItems,
                    pageNumber,
                    null,
                    null
                )
                val list = result.transactionList.map {
                    mapper.map(it)
                }
                emit(LoadResult.Success(Pair(list, result.totalNumOfItems)))
            } catch (e: Exception) {
                if (e is ConnectException) {
                    emit(LoadResult.NoConnection(e))
                } else {
                    emit(LoadResult.Error(e))

                }
            }

        }.flowOn(Dispatchers.IO)

    override suspend fun createTransaction(
        walletId: Int,
        amount: BigDecimal,
        type: TransactionType,
        category: Long,
        currency: String,
        date: LocalDateTime
    ) {
        transactionRemote.createTransaction(
            walletId,
            TransactionCreateBody(amount, type.toString(), category, currency, date)
        )
    }

    override suspend fun editingTransaction(
        id: Int,
        amount: BigDecimal?,
        type: TransactionType?,
        category: Long?,
        currency: String?,
        date: LocalDateTime?
    ) {
        transactionRemote.editingTransaction(
            id,
            TransactionEditBody(amount, type?.toString(), category, currency, date)
        )
    }

    override suspend fun deleteTransaction(id: Int) {
        transactionRemote.deleteTransaction(id)

    }

}