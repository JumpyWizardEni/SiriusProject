package com.siriusproject.coshelek.wallet_information.data.network

import android.util.Log
import com.siriusproject.coshelek.categories_info.data.model.Category
import com.siriusproject.coshelek.wallet_information.data.model.TransactionCreateBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionEditBody
import com.siriusproject.coshelek.wallet_information.data.model.TransactionInfo
import com.siriusproject.coshelek.wallet_information.data.model.TransactionRemoteModel
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject

class MockTransactionRemote @Inject constructor() : TransactionService {

    private var transactions = mutableListOf<TransactionRemoteModel>(
        TransactionRemoteModel(
            0,
            Category(0, "a", "INCOME", "0", 0),
            BigDecimal(1000),
            "RUB",
            "EXPENCE",
            LocalDateTime.now(),
        )
    )

    init {
        for (i in 1..160) {
            transactions.add(
                TransactionRemoteModel(
                    i,
                    Category(i.toLong(), "a", "INCOME", "0", 0),
                    BigDecimal(1000),
                    "RUB",
                    "INCOME",
                    LocalDateTime.now(),
                )
            )
        }
    }

    override suspend fun getTransactions(
        id: Int, numberOfItems: Int,
        pageNumber: Int,
        dateFrom: LocalDateTime?,
        dateTo: LocalDateTime?
    ): TransactionInfo {
        val newList = mutableListOf<TransactionRemoteModel>()
        for (i in 0 until transactions.size) {
            if ((i >= (pageNumber * numberOfItems)) and (i <= (pageNumber * numberOfItems + numberOfItems - 1))) {
                newList.add(transactions[i])
            }
        }
        Log.d(javaClass.name, "Page: $pageNumber, newListSize: ${newList.size}")

        return TransactionInfo(newList, newList.size.toLong())
    }

    override suspend fun createTransaction(walletId: Int, body: TransactionCreateBody) {
        transactions.add(
            TransactionRemoteModel(
                walletId,
                Category(0, "a", "", "", 0),
                body.amount,
                body.currency,
                body.type.toString(),
                LocalDateTime.now(),
            )
        )
    }

    override suspend fun editingTransaction(id: Int, body: TransactionEditBody) {
        val wallet = transactions.find {
            it.id == id
        }!!
        val index = transactions.indexOf(wallet)
        transactions.removeAt(index)
        with(body) {
            transactions.add(
                index,
                TransactionRemoteModel(
                    id,
                    if (category == null) {
                        Category(0, "a", "", "", 0)
                    } else wallet.category,
                    amount ?: wallet.amount,
                    currency ?: wallet.currency,
                    type?.toString() ?: wallet.type,
                    date ?: wallet.date,
                )
            )

        }
    }

    override suspend fun deleteTransaction(id: Int) {
        transactions = transactions.filter {
            it.id != id
        }.toMutableList()
    }

}