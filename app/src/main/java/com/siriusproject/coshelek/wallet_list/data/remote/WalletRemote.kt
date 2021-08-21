package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel

interface WalletRemote {

    suspend fun getWalletsList(): List<WalletRemoteModel>
}