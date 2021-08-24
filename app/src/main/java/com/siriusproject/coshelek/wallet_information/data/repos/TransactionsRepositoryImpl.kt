package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_information.data.model.*
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
    ): Flow<LoadResult<List<TransactionUiModel>>> =
        flow {
            try {
                val list = transactionRemote.getTransactions(
                    id,
                    TransactionInfoBody(
                        numberOfItems,
                        pageNumber,
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
                ).transactionsRemoteModel.map {
                    mapper.map(it)
                }
                emit(LoadResult.Success(list))
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
        category: String,
        currency: String,
        date: LocalDateTime
    ) {
        transactionRemote.createTransaction(
            walletId,
            TransactionCreateBody(amount, type, category, currency, date)
        )
    }

    override suspend fun editingTransaction(
        id: Int,
        amount: BigDecimal,
        type: TransactionType,
        category: String,
        currency: String,
        date: LocalDateTime
    ) {
        transactionRemote.editingTransaction(
            id,
            TransactionEditBody(amount, type, category, currency, date)
        )
    }

    override suspend fun deleteTransaction(id: Int) {
        transactionRemote.deleteTransaction(id)

    }

}