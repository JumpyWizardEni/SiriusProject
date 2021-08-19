package com.siriusproject.coshelek.wallet_information.data.repos

import com.siriusproject.coshelek.wallet_information.data.db.IDatabaseSource
import com.siriusproject.coshelek.wallet_information.data.db.TestDataManagerImpl
import com.siriusproject.coshelek.wallet_information.data.model.TransactionUiModel

class TestWalletReposImpl : IWalletRepos {

    //TODO DI
    private val dbSource: IDatabaseSource = TestDataManagerImpl()

    override fun getTransactions(): List<TransactionUiModel> {
        return dbSource.getTransactions()
    }


}