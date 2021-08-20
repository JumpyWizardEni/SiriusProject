package com.siriusproject.coshelek.wallet_information.data.db

import com.siriusproject.coshelek.wallet_information.data.model.CategoryUiModel
import com.siriusproject.coshelek.wallet_information.data.model.TransactionType
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import java.time.LocalDateTime

//Класс-заглушка для получения моделек с транзакциями
class TestDataManager : DataSource {
    override fun getTransactions(): List<TransactionUiModel> {
        return listOf(
            TransactionUiModel(
                0,
                "2",
                CategoryUiModel("Мой доход"),
                TransactionType.Income,
                13333,
                "",
                LocalDateTime.now()
            )
        )
    }

    override fun addTransaction(model: TransactionUiModel) {
        //TODO("Not yet implemented")
    }
}