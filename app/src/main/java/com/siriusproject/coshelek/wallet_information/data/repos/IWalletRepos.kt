package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel


//Пока что непонятно, будет ли 1 репозиторий, или они будут разделены по фичам
interface IWalletRepos {
    fun getTransactions(): List<TransactionUiModel>
}