package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import javax.inject.Inject

class WalletServerRemote @Inject constructor(private val service: WalletService) : WalletRemote {
    override suspend fun getWalletsList(): List<WalletRemoteModel> {
        return service.getWalletsList()
    }


}