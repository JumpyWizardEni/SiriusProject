package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.data.model.WalletTransactionInfo
import com.siriusproject.coshelek.wallet_information.data.network.TransactionService
import com.siriusproject.coshelek.wallet_information.domain.mapper.TransactionMapper
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.math.BigDecimal
import javax.inject.Inject

class TransactionsRepositoryImpl @Inject constructor(
    private val transactionRemote: TransactionService,
    private val mapper: TransactionMapper
) : TransactionsRepository {

    override suspend fun getTransactionsList(id: Int): Flow<List<WalletTransactionInfo>> =
        flow {
            val list = transactionRemote.getTransactionsList(id).map {
                mapper.map(it)
            }
            emit(list)
        }.flowOn(Dispatchers.IO)

    override suspend fun createTransaction(
        amount: BigDecimal,
        type: TransactionType,
        category: String,
        currency: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun editingTransaction(
        id: Int,
        amount: BigDecimal,
        type: TransactionType,
        category: String,
        currency: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(id: Int) {
        TODO("Not yet implemented")
    }

}