package com.siriusproject.coshelek.wallet_list.domain.use_cases

import com.siriusproject.coshelek.wallet_list.data.repos.WalletsRepository
import com.siriusproject.coshelek.wallet_list.domain.mappers.WalletMapper
import com.siriusproject.coshelek.wallet_list.ui.model.WalletUiModel
import javax.inject.Inject

class GetWalletsListUseCaseImpl @Inject constructor(
    private val repos: WalletsRepository,
    private val mapper: WalletMapper
) : GetWalletsUseCase {
    override suspend fun getWallets(): List<WalletUiModel> {
        val walletsUiList = mutableListOf<WalletUiModel>()
        val walletsList = repos.getWallets()
        walletsList.forEach {
            walletsUiList.add(mapper.map(it))
        }
        return walletsUiList
    }


}