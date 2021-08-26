package com.siriusproject.coshelek.wallet_list.data.remote

import com.siriusproject.coshelek.wallet_list.data.model.CurrencyModel
import com.siriusproject.coshelek.wallet_list.data.model.WalletChangeBody
import com.siriusproject.coshelek.wallet_list.data.model.WalletCreateBody
import com.siriusproject.coshelek.wallet_list.data.model.WalletRemoteModel
import kotlinx.coroutines.delay
import java.math.BigDecimal
import javax.inject.Inject

class MockServerRemote @Inject constructor() : WalletService {


    private var nextId = 1
    private var wallets = mutableListOf<WalletRemoteModel>(
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

    override suspend fun getWalletsList(): List<WalletRemoteModel> {
        delay(1000) //имитация загрузки
        return wallets
    }

    override suspend fun getWalletInfo(id: Int): WalletRemoteModel {
        val wallet = wallets.find {
            it.id == id
        }!!
        return WalletRemoteModel(
            0,
            wallet.name,
            wallet.balance!!,
            wallet.income!!,
            wallet.expense!!,
            "RUB",
            true,
            wallet.limit
        )
    }

    override suspend fun createWallet(body: WalletCreateBody) {

        wallets.add(
            WalletRemoteModel(
                nextId++,
                body.name,
                body.balance,
                BigDecimal(0),
                BigDecimal(0),
                body.currency,
                true,
                body.limit
            )
        )

    }

    override suspend fun changeWallet(id: Int, body: WalletChangeBody) {
        val wallet = wallets.find {
            it.id == id
        }!!
        val walletId = wallets.indexOf(wallet)
        val newModel = WalletRemoteModel(
            id,
            body.name ?: wallet.name,
            wallet.balance,
            wallet.income,
            wallet.expense,
            body.currency ?: wallet.currency,
            body.visibility ?: wallet.visibility,
            body.limit ?: wallet.limit
        )
        wallets.removeAt(walletId)
        wallets.add(walletId, newModel)
    }

    override suspend fun deleteWallet(id: Int) {
        wallets = wallets.filter {
            it.id != id
        }.toMutableList()
    }

    override suspend fun getCurrencies(): List<CurrencyModel> {
        return listOf(
            CurrencyModel("EUR", BigDecimal(80), "UP"),
            CurrencyModel("USD", BigDecimal(70), "UP")
        )

    }
}