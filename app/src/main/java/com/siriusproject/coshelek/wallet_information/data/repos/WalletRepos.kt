package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel


//Пока что непонятно, будет ли 1 репозиторий, или они будут разделены по фичам
interface WalletRepos {
    fun getTransactions(): List<TransactionUiModel>
}