package com.siriusproject.coshelek.wallet_list.data.repos

import com.siriusproject.coshelek.utils.LoadResult
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import kotlinx.coroutines.flow.Flow
import java.math.BigDecimal


interface WalletsRepository {

    suspend fun getWallets(): Flow<LoadResult<List<WalletUiModel>>>

    suspend fun getWalletInfo(id: Int, currency: String, visibility: Boolean): Flow<WalletUiModel>

    suspend fun createWallet(name: String, currency: String, balance: BigDecimal, limit: BigDecimal)

    suspend fun changeWallet(
        id: Int, name: String?,
        currency: String?,
        limit: BigDecimal?,
        visibility: Boolean?
    )

    suspend fun deleteWallet(id: Int)
}