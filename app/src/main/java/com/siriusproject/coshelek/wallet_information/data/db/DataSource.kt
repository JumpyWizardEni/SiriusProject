package com.siriusproject.coshelek.wallet_information.data.db

import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel

interface DataSource {

    fun getTransactions(): List<TransactionUiModel>

    fun addTransaction(model: TransactionUiModel)
}