package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.data.model.TransactionCreateBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionEditBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionInfoBody
import com.siriusproject.coshelek.wallet_information.data.network.TransactionService
import com.siriusproject.coshelek.wallet_information.domain.mapper.TransactionMapper
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.math.BigDecimal
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
    ): Flow<List<TransactionUiModel>> =
        flow {
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
            emit(list)
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