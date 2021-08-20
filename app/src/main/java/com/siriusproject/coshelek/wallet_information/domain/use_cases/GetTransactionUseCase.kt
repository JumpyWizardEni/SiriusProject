package com.siriusproject.coshelek.wallet_information.domain.use_cases

import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel
import com.siriusproject.coshelek.wallet_information.data.repos.TestWalletRepos
import com.siriusproject.coshelek.wallet_information.data.repos.WalletRepos

class GetTransactionUseCase {

    //TODO DI
    private val repos: WalletRepos = TestWalletRepos()

    fun getTransactions(): List<TransactionUiModel> {
        return repos.getTransactions()
    }
}