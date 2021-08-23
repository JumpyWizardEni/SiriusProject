package com.siriusproject.coshelek.wallet_information.domain.use_cases

import com.siriusproject.coshelek.wallet_information.data.repos.WalletRepos
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel
import javax.inject.Inject

class GetTransactionUseCase @Inject constructor(private val repos: WalletRepos) {

    fun getTransactions(): List<TransactionUiModel> {
        return repos.getTransactions()
    }
}