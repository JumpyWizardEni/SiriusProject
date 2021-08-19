package com.siriusproject.coshelek.wallet_information.data.db

import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

interface IDatabaseSource {

    fun getTransactions(): List<TransactionUiModel>

    fun addTransaction(model: TransactionUiModel)
}