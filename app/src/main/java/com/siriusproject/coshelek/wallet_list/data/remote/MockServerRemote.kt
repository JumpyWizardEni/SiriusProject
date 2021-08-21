package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import java.math.BigDecimal
import javax.inject.Inject

class MockServerRemote @Inject constructor() : WalletRemote {
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
}