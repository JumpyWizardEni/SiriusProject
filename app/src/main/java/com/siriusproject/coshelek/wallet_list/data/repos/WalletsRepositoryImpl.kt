package com.siriusproject.coshelek.wallet_list.data.repos

import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import com.siriusproject.coshelek.wallet_list.data.remote.WalletRemote
import javax.inject.Inject

class WalletsRepositoryImpl @Inject constructor(private val walletRemote: WalletRemote) :
    WalletsRepository {

    //TODO DB, error handling

    override suspend fun getWallets(): List<WalletRemoteModel> {
        return walletRemote.getWalletsList()
    }


}