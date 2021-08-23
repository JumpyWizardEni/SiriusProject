package com.siriusproject.coshelek.wallet_information.data.db

import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel
import javax.inject.Inject

//Класс-заглушка для получения моделек с транзакциями
class TestDataManager @Inject constructor() : DataSource {
    override fun getTransactions(): List<TransactionUiModel> {
        return listOf(

        )
    }

    override fun addTransaction(model: TransactionUiModel) {
        //TODO("Not yet implemented")
    }
}