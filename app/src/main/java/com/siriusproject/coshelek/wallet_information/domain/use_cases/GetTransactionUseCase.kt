package com.siriusproject.coshelek.wallet_information.domain.use_cases

import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.data.repos.IWalletRepos
import com.siriusproject.coshelek.wallet_information.data.repos.TestWalletReposImpl

class GetTransactionUseCase {

    //TODO DI
    private val repos: IWalletRepos = TestWalletReposImpl()

    fun getTransactions(): List<TransactionUiModel> {
        return repos.getTransactions()
    }
}