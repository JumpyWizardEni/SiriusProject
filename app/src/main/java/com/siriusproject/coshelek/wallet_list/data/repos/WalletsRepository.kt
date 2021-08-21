package com.siriusproject.coshelek.wallet_list.data.repos

import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel


interface WalletsRepository {

    suspend fun getWallets(): List<WalletRemoteModel>
}