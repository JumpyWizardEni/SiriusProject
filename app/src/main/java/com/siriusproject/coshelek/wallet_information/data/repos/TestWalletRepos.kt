package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.data.db.DataSource
import com.siriusproject.coshelek.wallet_information.data.db.TestDataManager
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

class TestWalletRepos : WalletRepos {

    //TODO DI
    private val dbSource: DataSource = TestDataManager()

    override fun getTransactions(): List<TransactionUiModel> {
        return dbSource.getTransactions()
    }


}