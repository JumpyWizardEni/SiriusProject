package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.data.db.DataSource
import com.siriusproject.coshelek.wallet_information.ui.model.TransactionUiModel
import javax.inject.Inject

class TestWalletRepos @Inject constructor(
    private val dbSource: DataSource,
) : WalletRepos {

    override fun getTransactions(): List<TransactionUiModel> {
        return dbSource.getTransactions()
    }


}