package com.siriusproject.coshelek.wallet_information.data.network

import com.siriusproject.coshelek.wallet_information.data.model.*
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

class MockTransactionRemote @Inject constructor() : TransactionService {

    private var transactions = mutableListOf(
        TransactionRemoteModel(
            0,
            "transaction 1",
            "category 1",
            BigDecimal(1000),
            "RUB",
            "INCOME",
            LocalDateTime.now(),
            BigDecimal(10000)
        )
    )

    override suspend fun getTransactions(id: Int, body: TransactionInfoBody): TransactionInfo {
        return TransactionInfo(transactions, transactions.size.toLong())
    }

    override suspend fun createTransaction(walletId: Int, body: TransactionCreateBody) {
        transactions.add(
            TransactionRemoteModel(
                walletId,
                body.type.toString(),
                body.category,
                body.amount,
                body.currency,
                body.type.toString(),
                LocalDateTime.now(),
                BigDecimal(10000)
            )
        )
    }

    override suspend fun editingTransaction(id: Int, body: TransactionEditBody) {
        //TODO("Not yet implemented")
    }

    override suspend fun deleteTransaction(id: Int) {
        transactions = transactions.filter {
            it.id != id
        }.toMutableList()
    }

}