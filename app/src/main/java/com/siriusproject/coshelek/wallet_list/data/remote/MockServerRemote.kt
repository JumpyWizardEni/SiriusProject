package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.WalletChangeBody
import com.siriusproject.coshelek.wallet_list.data.model.WalletCreateBody
import com.siriusproject.coshelek.wallet_list.data.model.WalletInfoRemoteModel
import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import java.math.BigDecimal
import javax.inject.Inject

class MockServerRemote @Inject constructor() : WalletService {
    override suspend fun getWalletsList(): List<WalletRemoteModel> {
        return listOf(
            WalletRemoteModel(
                0,
                "кошелек 1",
                BigDecimal(100),
                BigDecimal(100),
                BigDecimal(100),
                "руб",
                true,
                BigDecimal(100)
            )
        )
    }

    override suspend fun getWalletInfo(id: Int): WalletInfoRemoteModel {
        //TODO("Not yet implemented")
        return WalletInfoRemoteModel(
            "a",
            BigDecimal(0),
            BigDecimal(0),
            BigDecimal(0),
            BigDecimal(0)
        )
    }

    override suspend fun createWallet(body: WalletCreateBody) {
        //TODO("Not yet implemented")
    }

    override suspend fun changeWallet(id: Int, body: WalletChangeBody) {
        //TODO("Not yet implemented")
    }

    override suspend fun deleteWallet(id: Int) {
        //TODO("Not yet implemented")
    }
}