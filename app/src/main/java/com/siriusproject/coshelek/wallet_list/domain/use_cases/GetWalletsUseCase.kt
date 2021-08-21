package com.siriusproject.coshelek.wallet_list.domain.use_cases

import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel

interface GetWalletsUseCase {
    suspend fun getWallets(): List<WalletUiModel>
}